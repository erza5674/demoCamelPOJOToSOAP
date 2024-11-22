package com.example.demoCamelPOJOToSOAP.routes;

import com.example.demoCamelPOJOToSOAP.AbstractTest;
import com.example.demoCamelPOJOToSOAP.processor.RequestHandlerProcessor;
import com.example.demoCamelPOJOToSOAP.processor.ResponseHandlerProcessor;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@CamelSpringBootTest
public class CamelTest extends CamelTestSupport {
    @Override
    public RouteBuilder createRouteBuilder() throws Exception {
        return new RoutePOJOtoSOAP();
    }
    @Test
    public void sampleTest() throws InterruptedException {
        System.out.println("Test");
        template.sendBody("direct:shrek", "PL");
    }

}
