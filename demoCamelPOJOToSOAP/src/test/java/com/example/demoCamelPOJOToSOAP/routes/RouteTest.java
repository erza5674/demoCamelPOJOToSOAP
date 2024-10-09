package com.example.demoCamelPOJOToSOAP.routes;

import com.example.demoCamelPOJOToSOAP.AbstractTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.apache.camel.component.properties.ServicePropertiesFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.http;

@Slf4j
public class RouteTest extends AbstractTest {

    @Autowired
    CamelContext camelContext;

    @Autowired
    ProducerTemplate producerTemplate;

    @BeforeEach
    void initTest() throws Exception{

        //создаем стаб на адресс /test
        toServiceMockServer.stubFor(post(urlEqualTo("/test")).willReturn(aResponse().withBody("All OK")));
        //создаем роут
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                //создаем эндпоинт совпадающий со стабом созданным ранее, на который будем стучаться
                org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory.HttpEndpointBuilder endpoint = http("localhost:34001/test")
                        .bridgeEndpoint(true);

                //Непосредственно роут который будем тестировать
                from(direct("test-country-whatever"))
                        .streamCaching()
                        .log(LoggingLevel.INFO, log, "OAUTH REQUEST:\n${headers}\n${body}")
                        .to(endpoint)
                        .convertBodyTo(String.class);
            }
        });

    }

    @Test
    void check(){

        //достаем ответ от нашего стаба
        var response = producerTemplate.requestBodyAndHeader("direct:test-country-whatever", "test", "CamelHttpMethod", "POST");

        Assertions.assertEquals("All OK", response.toString());
    }
}


