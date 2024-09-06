package com.example.demoCamelPOJOToSOAP.processor.request;

import com.example.demoCamelPOJOToSOAP.processor.RequestProcessor;
import com.example.demoCamelPOJOToSOAP.service.Country;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class CountryRequestProcessor implements org.apache.camel.Processor{

    @Override
    public void process(Exchange exchange) throws Exception {
        Country country = exchange.getMessage().getBody(Country.class);
        log.info(country.getName());
    }
}
