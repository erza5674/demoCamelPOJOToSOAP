package com.example.demoCamelPOJOToSOAP.config;

import com.example.demoCamelPOJOToSOAP.processor.RequestHandlerProcessor;
import com.example.demoCamelPOJOToSOAP.processor.RequestProcessor;
import com.example.demoCamelPOJOToSOAP.processor.ResponseHandlerProcessor;
import com.example.demoCamelPOJOToSOAP.processor.ResponseProcessor;
import com.example.demoCamelPOJOToSOAP.service.CountryRepository;
import com.example.demoCamelPOJOToSOAP.service.CountryService;
import org.apache.camel.CamelContext;
import org.apache.camel.component.cxf.common.DataFormat;
import org.apache.camel.component.cxf.jaxws.CxfEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class SpringBootConfig {

    @Autowired
    CamelContext camelContext;

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public CountryRepository countryRepository() {
       CountryRepository countryRepository = new CountryRepository();

       return countryRepository;
    }

    @Bean
    CxfEndpoint country() {
        CxfEndpoint countryEndpoint = new CxfEndpoint();
        countryEndpoint.setServiceClass(CountryService.class);
        countryEndpoint.setAddress("/country");
        countryEndpoint.setDataFormat(DataFormat.POJO);

        return countryEndpoint;
    }

    @Bean
    public RequestHandlerProcessor requestHandlerProcessor(){ //хэндлер реквест процессоров
        RequestHandlerProcessor requestHandlerProcessor = new RequestHandlerProcessor(requestProcessorList());

        return requestHandlerProcessor;
    }

    @Bean
    public ResponseHandlerProcessor responseHandlerProcessor(){ //хэндлер респонс процессоров
        ResponseHandlerProcessor responseHandlerProcessor = new ResponseHandlerProcessor(responseProcessorList());

        return responseHandlerProcessor;
    }

    @Bean
    public List<RequestProcessor> requestProcessorList(){ //возвращаем список бинов процессоров
         List<RequestProcessor> listBeans = new ArrayList<>();

         Map<String, RequestProcessor> beans = applicationContext.getBeansOfType(RequestProcessor.class);

         for (var element: beans.entrySet()){
             listBeans.add(element.getValue());
         }

        return listBeans;
    }

    @Bean
    public List<ResponseProcessor> responseProcessorList(){ //возвращаем список бинов процессоров
        List<ResponseProcessor> listBeans = new ArrayList<>();

        Map<String, ResponseProcessor> beans = applicationContext.getBeansOfType(ResponseProcessor.class);

        for (var element: beans.entrySet()){
            listBeans.add(element.getValue());
        }

        return listBeans;
    }
}
