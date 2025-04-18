package com.example.demoCamelPOJOToSOAP.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Slf4j
@Component
public class ResponseHandlerProcessor implements org.apache.camel.Processor {
    private Map<String, ResponseProcessor> mapOperations;

    @Autowired
    public ResponseHandlerProcessor(List<ResponseProcessor> listBeans) {
        this.mapOperations = listBeans.stream()
                .collect(toMap(ResponseProcessor::getOperationName, Function.identity()));
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        String operationName = (String) exchange.getMessage().getHeader("operationName");

        if (operationName == null && operationName.isEmpty()) {
            log.error("Operation '{}' is not detected in service!!!", operationName);
            throw new UnsupportedOperationException("Operation " + operationName + " is not detected in service!");
        }

        ResponseProcessor responseProcessor = mapOperations.get(operationName);

        if (responseProcessor == null) {
            log.error("Operation '{}' - is not implemented", operationName);
            throw new UnsupportedOperationException("Operation " + operationName + " - is not implemented");
        }

        responseProcessor.prepareResponse(exchange);
    }
}
