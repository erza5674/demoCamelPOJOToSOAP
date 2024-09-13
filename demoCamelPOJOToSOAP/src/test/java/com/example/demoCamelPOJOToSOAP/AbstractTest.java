package com.example.demoCamelPOJOToSOAP;

import com.example.demoCamelPOJOToSOAP.extensions.NoKeepAliveTransformer;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@AutoConfigureObservability
public class AbstractTest {

    protected final static WireMockExtension toServiceMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().port(34001).globalTemplating(true).extensions(NoKeepAliveTransformer.class))
            .configureStaticDsl(true).build();

}
