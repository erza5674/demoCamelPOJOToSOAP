package com.example.demoCamelPOJOToSOAP.service;

import com.example.demoCamelPOJOToSOAP.exceptions.NoSuchCountryException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CountryRepository {
    private Map<String, Country> countryRepository = new ConcurrentHashMap<>();

    public void addCountry(Country country){
        countryRepository.put(country.getName(),country);
    }

    public Country getCountryByName(String name) throws NoSuchCountryException {
        if (!countryRepository.containsKey(name)) {
            throw new NoSuchCountryException();
        }
        return countryRepository.get(name);
    }

    public String updateCountry(Country country) throws NoSuchCountryException {
        String result = "Country" + country.getName() + " updated" ;

        if (!countryRepository.containsKey(country.getName())) {
            throw new NoSuchCountryException("errore!");
        }
        countryRepository.replace(country.getName(), country);
        return result;
    }

    public String deleteCountry(String name) throws NoSuchCountryException {
        String result = "Country" + name + "deleted";
        if (!countryRepository.containsKey(name)) {
            throw new NoSuchCountryException();
        }
        countryRepository.remove(name);
        return result;
    }
}
