package com.example.demoCamelPOJOToSOAP.processor.request;

import com.example.demoCamelPOJOToSOAP.processor.RequestProcessor;
import com.example.demoCamelPOJOToSOAP.service.Country;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.oorsprong.websamples.FullCountryInfo;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class CountryRequestProcessor implements org.apache.camel.Processor{

    @Override
    public void process(Exchange exchange) throws Exception {

    }
}
