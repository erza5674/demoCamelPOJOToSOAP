package com.example.demoCamelPOJOToSOAP.processor.response;

import com.example.demoCamelPOJOToSOAP.processor.ResponseProcessor;
import com.example.demoCamelPOJOToSOAP.service.Country;
import com.example.demoCamelPOJOToSOAP.service.CountryRepository;
import jakarta.xml.ws.Holder;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.oorsprong.websamples.FullCountryInfo;
import org.oorsprong.websamples.FullCountryInfoResponse;
import org.oorsprong.websamples.TCountryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetCountryISOResponse implements ResponseProcessor {

    @Autowired
    private CountryRepository countryRepository;


    @Override
    public void prepareResponse(Exchange exchange) {
        log.info("Add Country by iso Response processor START");

        //получаем ответ
        TCountryInfo response;
        response= exchange.getMessage().getBody(TCountryInfo.class);

        log.info("Recived country :" + response.getSName().toString());
        exchange.getMessage().setBody("Recived country " + response.getSName().toString());


        var body = exchange.getMessage().getBody().toString();
        if (body.equals("Recived country Country not found in the database")){
            return;
        }

        Country country = new Country();

        country.setName(response.getSName());
        country.setCapital(response.getSCapitalCity());
        country.setHaveNukes(false);
        country.setPresident("");

        countryRepository.addCountry(country);
    }

    @Override
    public String getOperationName() {
        return "FullCountryInfo";
    }
}
