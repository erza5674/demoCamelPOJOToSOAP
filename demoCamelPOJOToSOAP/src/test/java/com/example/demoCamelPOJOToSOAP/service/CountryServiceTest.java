package com.example.demoCamelPOJOToSOAP.service;

import com.example.demoCamelPOJOToSOAP.exceptions.NoSuchCountryException;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryServiceTest {

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

        Assertions.assertThat(countryService.getCountryByName("Russia")).isNotNull();

        Assertions.assertThat(country.getName()).isEqualTo(countryRes.getName());
    }

    @Test
    public void serviceExceptionTest(){
        CountryService countryService = createCXFClient();

        Assertions.assertThatThrownBy(() -> countryService.getCountryByName("none")).isInstanceOf(SoapFault.class);

        Assertions.assertThatException().isThrownBy(()->countryService.getCountryByName("rer"));
    }

}
