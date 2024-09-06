package com.example.demoCamelPOJOToSOAP.routes;

import com.example.demoCamelPOJOToSOAP.service.Country;
import com.example.demoCamelPOJOToSOAP.service.CountryService;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouteTest {

    @LocalServerPort
    private int port;

    protected CountryService createCXFClient(){
        String URL = "http://localhost:" + port + "/services/country";

        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
        factory.setServiceClass(CountryService.class);
        factory.setAddress(URL);

        return (CountryService) factory.create();
    }
    @Test
    public void serviceTest(){
        CountryService countryService = createCXFClient();

        Country country = new Country();
        country.setCapital("Moscow");
        country.setPresident("Putin");
        country.setHaveNukes(true);
        country.setName("Russia");

        countryService.addCountry(country);

        Country countryRes = countryService.getCountryByName("Russia");

        Assertions.assertThat(country.getName()).isEqualTo(countryRes.getName());
    }
}
