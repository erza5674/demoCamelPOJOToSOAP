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
    private final RequestHandlerProcessor requestHandlerProcessor;
    @Autowired
    private final ResponseHandlerProcessor responseHandlerProcessor;

    public RoutePOJOtoSOAP(RequestHandlerProcessor requestHandlerProcessor, ResponseHandlerProcessor responseHandlerProcessor){
        this.requestHandlerProcessor = requestHandlerProcessor;
        this.responseHandlerProcessor = responseHandlerProcessor;
    }



    @Bean
    CxfEndpoint country() {
        CxfEndpoint countryEndpoint = new CxfEndpoint();
        countryEndpoint.setServiceClass(CountryService.class);
        countryEndpoint.setAddress("/country");
        countryEndpoint.setDataFormat(DataFormat.POJO);

        return countryEndpoint;
    }

    @Override
    public void configure() throws Exception {

        CxfEndpointBuilderFactory.CxfEndpointProducerBuilder externalEndpoint = getExternalEndpoint();

//        onException(Exception.class)
//                .log("BEEEEEEEEEEEEEEP")
//                .process((exchange)->{
//                    System.out.println("im called from lambda processor");
//
//                    var headers = exchange.getIn().getHeaders().toString();
//                    var body    = exchange.getIn().getBody().toString();
//                    System.out.println("HEADERS: " + headers);
//                    System.out.println("BODY: " + body);
//                } )
//                .handled(true);

//        from("cxf:bean:country")
//                .log("take me home")
//                .routeId("CountryRoute")
//                .recipientList(simple("bean:CountryServiceImpl?method=${header.operationName}"))
//                .process(new CountryRequestProcessor())
//                .process(requestHandlerProcessor);

        from("cxf:bean:country")
                .routeId("CountryRoute")
                .log("start")
                .recipientList(simple("bean:CountryServiceImpl?method=${header.operationName}"))
                .choice()
                    .when(header("operationName").isEqualTo("addCountryByISO"))
                        .removeHeader(CxfConstants.OPERATION_NAMESPACE)
                        .process(requestHandlerProcessor)
                        .removeHeader("CamelCxfMessage")
                        .removeHeader("CamelAuthentication")
                        .to(externalEndpoint)
                        .process(responseHandlerProcessor)
                    .end()
                .log("end");
    }

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
}
