package com.example.demoCamelPOJOToSOAP.service;

import com.example.demoCamelPOJOToSOAP.exceptions.NoSuchCountryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


public class CountryRepositoryTest {
    private CountryRepository countryRepository = new CountryRepository();

    @Test
    public void getCountryByNameTestExeption(){
        NoSuchCountryException exception = Assertions.assertThrows(NoSuchCountryException.class, () -> countryRepository.getCountryByName("tesy"));
        Assertions.assertEquals(NoSuchCountryException.class,exception.getClass());
    }

    @Test
    public void addCounty(){
        Country country = new Country();
        country.setName("Russia");
        country.setPresident("Vladimir Putin");
        country.setHaveNukes(true);
        country.setCapital("Moscow");
        countryRepository.addCountry(country);

        Country countryResult = null;
        try {
            countryResult = countryRepository.getCountryByName("Russia");
        } catch (NoSuchCountryException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals( country.getName(), countryResult.getName());
    }
}
