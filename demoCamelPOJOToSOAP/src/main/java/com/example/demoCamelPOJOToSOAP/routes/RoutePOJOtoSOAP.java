package com.example.demoCamelPOJOToSOAP.routes;


import com.example.demoCamelPOJOToSOAP.processor.RequestHandlerProcessor;
import com.example.demoCamelPOJOToSOAP.processor.ResponseHandlerProcessor;
import com.example.demoCamelPOJOToSOAP.processor.request.CountryRequestProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.apache.camel.builder.endpoint.dsl.CxfEndpointBuilderFactory;
import org.apache.camel.component.cxf.common.DataFormat;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.component.cxf.jaxws.CxfEndpoint;

import org.oorsprong.websamples_countryinfo.CountryInfoService;
import org.oorsprong.websamples_countryinfo.CountryInfoServiceSoapType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.demoCamelPOJOToSOAP.service.CountryService;


@Component
public class RoutePOJOtoSOAP extends RouteBuilder {

    @Value("${service.externalEndpoint.url}")
    private String EXT_URL;

    @Autowired
    private RequestHandlerProcessor requestHandlerProcessor;
    @Autowired
    private ResponseHandlerProcessor responseHandlerProcessor;

    @Autowired
    private CxfEndpoint countryEndpoint;

//    public RoutePOJOtoSOAP(RequestHandlerProcessor requestHandlerProcessor, ResponseHandlerProcessor responseHandlerProcessor){
//        this.requestHandlerProcessor = requestHandlerProcessor;
//        this.responseHandlerProcessor = responseHandlerProcessor;
//    }



//    @Bean
//    CxfEndpoint country() {
//        CxfEndpoint countryEndpoint = new CxfEndpoint();
//        countryEndpoint.setServiceClass(CountryService.class);
//        countryEndpoint.setAddress("/country");
//        countryEndpoint.setDataFormat(DataFormat.POJO);
//
//        return countryEndpoint;
//    }

    private CxfEndpointBuilderFactory.CxfEndpointProducerBuilder getExternalEndpoint(){
        String WSDL_LOCATION = "classpath:wsdl/CountryInfoService.wsdl";
        CxfEndpointBuilderFactory.CxfEndpointProducerBuilder externalEndpoint;

        externalEndpoint = StaticEndpointBuilders
                .cxf(EXT_URL) //тут нужно передавать ссылку на сервис, но не wsdl, иначе сломается
                .dataFormat(DataFormat.POJO)
                .wsdlURL(WSDL_LOCATION) //тут ссылка на WSDL который храним у себя
                .serviceClass(CountryInfoServiceSoapType.class.getName()); //Здесь нужно передать имя ИНТЕРЕФЕЙС сервисного класса, если передать на сам класс, то будет ошибка с портом

        return  externalEndpoint;
    }

    @Override
    public void configure() throws Exception {

        CxfEndpointBuilderFactory.CxfEndpointProducerBuilder externalEndpoint = getExternalEndpoint();


//        from("cxf:bean:country")
        from(countryEndpoint)
                .routeId("CountryRoute")
                .log("start")
                .recipientList(simple("bean:CountryServiceImpl?method=${header.operationName}"))
                .choice()
                    .when(header("operationName").isEqualTo("addCountryByISO"))
                        .removeHeader(CxfConstants.OPERATION_NAMESPACE)
                        .process(requestHandlerProcessor)
                        .removeHeader("CamelCxfMessage")
                        .removeHeader("CamelAuthentication")
                        .log("Sending body: " + body().toString())
                        .log("Sending header: " + "${headers}" )
                        .to(externalEndpoint).id("externalEndpoint")
                        .process(responseHandlerProcessor)
                    .end()
                .log("end");
    }


}
