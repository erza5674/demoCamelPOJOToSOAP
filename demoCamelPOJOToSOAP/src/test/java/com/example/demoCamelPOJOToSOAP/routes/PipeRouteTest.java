package com.example.demoCamelPOJOToSOAP.routes;

import com.example.demoCamelPOJOToSOAP.AbstractTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class PipeRouteTest extends AbstractTest {

    private static final String ROUTE_PATH = "/services/country";

    @Test
    void pipeTest(){
        final String requestBody = "{ \"test\" : 1 }";
        final String responseBody = "{ \"test\" : 1 }";

        stubFor(
                post(urlEqualTo(""))
                        .withRequestBody(equalToJson(requestBody))
                        .withHeader(CONTENT_TYPE, equalTo(APPLICATION_JSON_VALUE))
                        .willReturn(
                                ok()
                                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                                        .withBody(responseBody)
                        )
        );

        given()
                .body(requestBody)
                .post(ROUTE_PATH)
                .then()
                .assertThat()
                .statusCode(302)
                .contentType(ContentType.JSON)
                .body(is(responseBody));
    }
}
