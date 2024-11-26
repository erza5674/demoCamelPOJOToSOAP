package com.example.demoCamelPOJOToSOAP.service;

import com.example.demoCamelPOJOToSOAP.exceptions.NoSuchCountryException;
import org.apache.camel.Exchange;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.oorsprong.websamples.FullCountryInfo;
import org.oorsprong.websamples.FullCountryInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("CountryServiceImpl")
public class CountryServiceImpl implements CountryService{
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public void addCountry(Country country) {
        countryRepository.addCountry(country);
        System.out.println("Country Added");
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

    @Override
    public String addCountryByISO(String iso) throws NoSuchCountryException {

        return iso;
    }


}
