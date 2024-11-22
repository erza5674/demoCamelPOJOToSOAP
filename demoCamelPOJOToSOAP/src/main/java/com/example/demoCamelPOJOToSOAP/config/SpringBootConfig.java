package com.example.demoCamelPOJOToSOAP.config;

import com.example.demoCamelPOJOToSOAP.service.CountryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBootConfig {


    @Bean
    public CountryRepository countryRepository() {
       CountryRepository countryRepository = new CountryRepository();

       return countryRepository;
    }
}
