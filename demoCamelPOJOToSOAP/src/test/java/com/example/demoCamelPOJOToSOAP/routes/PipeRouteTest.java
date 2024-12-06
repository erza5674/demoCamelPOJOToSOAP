package com.example.demoCamelPOJOToSOAP.routes;

import com.example.demoCamelPOJOToSOAP.AbstractTest;
import io.restassured.http.ContentType;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.AdviceWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

public class PipeRouteTest extends AbstractTest {

    @Autowired
    private CamelContext camelContext; //Контекст цепляется для AdwiseWith цыганской магии. Бин определяется в конфиге

    private static final String ROUTE_PATH = "/services/country";

//    private static String EXTERNAL_ENDPOINT_NAME_PATTERN = "cxf://http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?dataFormat=POJO&serviceClass=org.oorsprong.websamples_countryinfo.CountryInfoServiceSoapType&wsdlURL=classpath:wsdl/CountryInfoService.wsdl";
    private static String EXTERNAL_ENDPOINT_NAME_PATTERN = "externalEndpoint";

    @BeforeEach
    public void beforeEach() throws Exception {

        //TODO тут по идее надо подменить ендпоинт который отправляет данные на внешний соап сервер
        AdviceWith.adviceWith(this.camelContext, "CountryRoute", route ->{
            route.weaveById(EXTERNAL_ENDPOINT_NAME_PATTERN).replace().to("stub:services/country"); //подменяем продюсер эндпоинт на мок
        });
        camelContext.start();
    }

    @Test
    void TestMockForProvider(){

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

        final String stubResponseXML = """
                <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns1:addCountryByISOResponse xmlns:ns1="http://service.demoCamelPOJOToSOAP.example.com/"><ns2:return xmlns:ns2="http://service.demoCamelPOJOToSOAP.example.com/">Recived country Gotland!</ns2:return></ns1:addCountryByISOResponse></soap:Body></soap:Envelope>""";

        stubFor(
                any(urlEqualTo("/services/country"))
                        .willReturn(
                                ok()
                                        .withHeader(CONTENT_TYPE, APPLICATION_XML_VALUE)
                                        .withBody(stubResponseXML)
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
                post(urlEqualTo("/"))
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
                post(urlEqualTo("/"))
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
                post(urlEqualTo("/"))
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

