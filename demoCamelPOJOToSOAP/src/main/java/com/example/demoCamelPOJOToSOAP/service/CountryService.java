package com.example.demoCamelPOJOToSOAP.service;

import com.example.demoCamelPOJOToSOAP.exceptions.NoSuchCountryException;
import org.apache.camel.Exchange;

public interface CountryService {
    void addCountry(Country country);

    Country getCountryByName(String name);

    String updateCounty(Country country) throws NoSuchCountryException;

    String deleteCountry(String name) throws NoSuchCountryException;

    String addCountryByISO(String iso) throws NoSuchCountryException;
}
