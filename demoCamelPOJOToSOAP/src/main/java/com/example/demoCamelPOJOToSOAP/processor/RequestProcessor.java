package com.example.demoCamelPOJOToSOAP.processor;

import org.apache.camel.Exchange;

public interface RequestProcessor {
    // реализация подготовки SOAP message для метода Спарк
    void prepareRequest(Exchange exchange);
    // Имя операции, для которой идет подготовка SOAP request
    String getOperationName();
}
