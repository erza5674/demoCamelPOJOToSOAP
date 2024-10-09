package com.example.demoCamelPOJOToSOAP.routes;


import com.example.demoCamelPOJOToSOAP.processor.RequestHandlerProcessor;
import com.example.demoCamelPOJOToSOAP.processor.request.CountryRequestProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.DataFormat;
import org.apache.camel.component.cxf.jaxws.CxfEndpoint;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.demoCamelPOJOToSOAP.service.CountryService;

import java.nio.file.AccessDeniedException;

@Component
public class RoutePOJOtoSOAP extends RouteBuilder {
    private final RequestHandlerProcessor requestHandlerProcessor;

    public RoutePOJOtoSOAP(RequestHandlerProcessor requestHandlerProcessor){
        this.requestHandlerProcessor = requestHandlerProcessor;
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

        onException(Exception.class)
                .log("BEEEEEEEEEEEEEEP")
                .process((exchange)->{
                    System.out.println("im called from lambda processor");

                    var headers = exchange.getIn().getHeaders().toString();
                    var body    = exchange.getIn().getBody().toString();
                    System.out.println("HEADERS: " + headers);
                    System.out.println("BODY: " + body);
                } )
                .handled(true);

        onException(IllegalArgumentException.class)
                .log("Illegal Argument BEEEEEEEEEEEEEEEEP")
                .handled(true);

        from("cxf:bean:country")
                .log("take me home")
                .routeId("CountryRoute")
                .recipientList(simple("bean:CountryServiceImpl?method=${header.operationName}"))
                .process(new CountryRequestProcessor())
                .process(requestHandlerProcessor);
    }
}
