package com.example.demoCamelPOJOToSOAP.routes;

import com.example.demoCamelPOJOToSOAP.AbstractTest;
import com.example.demoCamelPOJOToSOAP.config.SpringBootConfig;
import com.example.demoCamelPOJOToSOAP.processor.RequestHandlerProcessor;
import com.example.demoCamelPOJOToSOAP.processor.ResponseHandlerProcessor;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

//кажется что этот подход не сработает, т.к. camelTestSupport не поддерживает спринг
@CamelSpringBootTest
@ContextConfiguration(classes = SpringBootConfig.class)
public class CamelTest extends CamelTestSupport {

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();

        context().
    }

    @Override
    public RouteBuilder createRouteBuilder() throws Exception {
        return new RoutePOJOtoSOAP();
    }
    @Test
    public void sampleTest() throws InterruptedException {
        System.out.println("Test");
        template.sendBody("cxf://bean:country", "PL");
    }

}
