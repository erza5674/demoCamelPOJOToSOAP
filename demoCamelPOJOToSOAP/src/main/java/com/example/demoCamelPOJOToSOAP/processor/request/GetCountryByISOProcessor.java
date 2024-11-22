package com.example.demoCamelPOJOToSOAP.processor.request;

import com.example.demoCamelPOJOToSOAP.processor.RequestProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.oorsprong.websamples.FullCountryInfo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetCountryByISOProcessor implements RequestProcessor {
    @Override
    public void prepareRequest(Exchange exchange) {
        log.info("Adding country by ISO request Processor START");

        FullCountryInfo fullCountryInfo = exchange.getMessage().getBody(FullCountryInfo.class);

        //set header value for producer request"
        exchange.getMessage().setHeader(CxfConstants.OPERATION_NAME, "FullCountryInfo"); //название операции
        exchange.getMessage().setHeader("SOAPAction", "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso/FullCountryInfo"); //URl куда кидать


        //preparind data for body
        String iso = exchange.getMessage().getBody().toString();

        //setting body
        MessageContentsList mList = new MessageContentsList(new Object[] {iso});
        exchange.getMessage().setBody(mList);
    }

    @Override
    public String getOperationName() {
        return "addCountryByISO";
    }
}
