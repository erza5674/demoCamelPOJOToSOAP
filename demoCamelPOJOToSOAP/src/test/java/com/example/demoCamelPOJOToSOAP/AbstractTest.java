package com.example.demoCamelPOJOToSOAP;

import com.tngtech.keycloakmock.api.KeycloakMock;
import com.tngtech.keycloakmock.api.ServerConfig;
import io.restassured.RestAssured;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureObservability
public class AbstractTest {

    public final static KeycloakMock keycloackMockServer;

    static {
        RestAssured.baseURI = "http://localhost";

        keycloackMockServer = new KeycloakMock( ServerConfig.aServerConfig().withPort(34009).withDefaultRealm("DEV").build());
    }
}
