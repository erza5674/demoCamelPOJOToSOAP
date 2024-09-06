package com.example.demoCamelPOJOToSOAP.processor.request;

import com.example.demoCamelPOJOToSOAP.processor.RequestProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateCountryProcessor implements RequestProcessor {
    @Override
    public void prepareRequest(Exchange exchange) {

    }

    @Override
    public String getOperationName() {
        return "updateCountry";
    }
}
