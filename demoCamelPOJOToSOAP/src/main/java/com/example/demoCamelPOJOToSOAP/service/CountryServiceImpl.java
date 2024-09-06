package com.example.demoCamelPOJOToSOAP.service;

import com.example.demoCamelPOJOToSOAP.exceptions.NoSuchCountryException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("CountryServiceImpl")
public class CountryServiceImpl implements CountryService{
    private CountryRepository countryRepository = new CountryRepository();

    @Override
    public void addCountry(Country country) {
        countryRepository.addCountry(country);
//        System.out.println("kek");
    }

    @Override
    public Country getCountryByName(String name) {
//        System.out.println("bepl");

        Country country = null;
        try {
            country = countryRepository.getCountryByName(name);
        } catch (NoSuchCountryException e) {
            throw new RuntimeException(e);
        }
        return country;
    }

    @Override
    public String updateCounty(Country country) throws NoSuchCountryException {
        return countryRepository.updateCountry(country);
    }

    @Override
    public String deleteCountry(String name) throws NoSuchCountryException {
        return countryRepository.deleteCountry(name);
    }
}
