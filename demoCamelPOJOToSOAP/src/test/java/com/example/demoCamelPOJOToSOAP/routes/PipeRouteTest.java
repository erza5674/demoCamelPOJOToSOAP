package com.example.demoCamelPOJOToSOAP.routes;

import com.example.demoCamelPOJOToSOAP.AbstractTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

public class PipeRouteTest extends AbstractTest {

    private static final String ROUTE_PATH = "/services/country";

    @Test
    void addCountryTest(){
        final String requestXml = """
                                        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.demoCamelPOJOToSOAP.example.com/">
                                        <soapenv:Header/>
                                           <soapenv:Body>
                                              <ser:addCountry>
                                                 <!--Optional:-->
                                                 <ser:arg0>
                                                    <!--Optional:-->
                                                    <capital>Moscow</capital>
                                                    <!--Optional:-->
                                                    <haveNukes>true</haveNukes>
                                                    <!--Optional:-->
                                                    <name>Russia</name>
                                                    <!--Optional:-->
                                                    <president>Putin</president>
                                                 </ser:arg0>
                                              </ser:addCountry>
                                           </soapenv:Body>
                                        </soapenv:Envelope>
                                        """;

        final String responseXml = """
                <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns1:addCountryResponse xmlns:ns1="http://service.demoCamelPOJOToSOAP.example.com/"></ns1:addCountryResponse></soap:Body></soap:Envelope>""";


        stubFor(
                post(urlEqualTo(""))
                        .withRequestBody(equalToXml(requestXml))
                        .withHeader(CONTENT_TYPE, equalTo(APPLICATION_XML_VALUE))
                        .willReturn(
                                ok()
                                        .withHeader(CONTENT_TYPE, APPLICATION_XML_VALUE)
                                        .withBody(responseXml)
                        )
        );

        given()
                .body(requestXml)
                .post(ROUTE_PATH)
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.XML)
                .body(is(responseXml));
    }

    @Test
    void addCountryByISOTest(){

        final String requestXML = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.demoCamelPOJOToSOAP.example.com/">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <ser:addCountryByISO>
                         <!--Optional:-->
                         <ser:arg0>PL</ser:arg0>
                      </ser:addCountryByISO>
                   </soapenv:Body>
                </soapenv:Envelope>
                """;
        final String responseXML = """
                <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns1:addCountryByISOResponse xmlns:ns1="http://service.demoCamelPOJOToSOAP.example.com/"><ns2:return xmlns:ns2="http://service.demoCamelPOJOToSOAP.example.com/">Recived country Poland</ns2:return></ns1:addCountryByISOResponse></soap:Body></soap:Envelope>""";

        stubFor(
                post(urlEqualTo(""))
                        .withRequestBody(equalToXml(requestXML))
                        .withHeader(CONTENT_TYPE, equalTo(APPLICATION_XML_VALUE))
                        .willReturn(
                                ok()
                                        .withHeader(CONTENT_TYPE, APPLICATION_XML_VALUE)
                                        .withBody(responseXML)
                        )
        );

        given()
                .body(requestXML)
                .post(ROUTE_PATH)
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.XML)
                .body(is(responseXML));
    }

    @Test
    void getCountryByName(){
        final String requestXML = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.demoCamelPOJOToSOAP.example.com/">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <ser:getCountryByName>
                         <!--Optional:-->
                         <ser:arg0>Russia</ser:arg0>
                      </ser:getCountryByName>
                   </soapenv:Body>
                </soapenv:Envelope>
                """;
        final String responseXML = """
                <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns1:getCountryByNameResponse xmlns:ns1="http://service.demoCamelPOJOToSOAP.example.com/"><ns2:return xmlns:ns2="http://service.demoCamelPOJOToSOAP.example.com/"><capital>Moscow</capital><haveNukes>true</haveNukes><name>Russia</name><president>Putin</president></ns2:return></ns1:getCountryByNameResponse></soap:Body></soap:Envelope>""";
        stubFor(
                post(urlEqualTo(""))
                        .withRequestBody(equalToXml(requestXML))
                        .withHeader(CONTENT_TYPE, equalTo(APPLICATION_XML_VALUE))
                        .willReturn(
                                ok()
                                        .withHeader(CONTENT_TYPE, APPLICATION_XML_VALUE)
                                        .withBody(responseXML)
                        )
        );

        given()
                .body(requestXML)
                .post(ROUTE_PATH)
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.XML)
                .body(is(responseXML));
    }
}
