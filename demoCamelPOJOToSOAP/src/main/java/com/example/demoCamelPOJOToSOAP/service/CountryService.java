package com.example.demoCamelPOJOToSOAP.service;

import com.example.demoCamelPOJOToSOAP.exceptions.NoSuchCountryException;

public interface CountryService {
    void addCountry(Country country);

    Country getCountryByName(String name);

    String updateCounty(Country country) throws NoSuchCountryException;

    String deleteCountry(String name) throws NoSuchCountryException;
}
