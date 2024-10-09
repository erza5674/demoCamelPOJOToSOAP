package com.example.demoCamelPOJOToSOAP;

import com.example.demoCamelPOJOToSOAP.extensions.NoKeepAliveTransformer;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@AutoConfigureObservability
public class AbstractTest { //NOSONAR

    @RegisterExtension
    protected final static WireMockExtension toServiceMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig()
                    .port(34001)
                    .globalTemplating(true)
                    .extensions(NoKeepAliveTransformer.class)
            )
            .configureStaticDsl(true)
            .build();

    static {
        RestAssured.baseURI = "http://localhost";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setAccept(ContentType.ANY)
                .setContentType(ContentType.JSON)
                .build()
                .log().all(true);
    }

}
