package com.example.demoCamelPOJOToSOAP.processor;
import org.apache.camel.Exchange;

/*
 * Интерфейс для подготовки response Soap message инициатору запроса
 * prepareResponse - подготовка response Soap message для инициатору запроса
 *   - входной параметр - exchange - полностью exchange Camel
 *
 * getOperationName - какой именно operationName необходимо подготовить (operationName  - header inbound message cxf)
 * */

public interface ResponseProcessor {
    // реализация подготовки SOAP message для инициатору запроса
    void prepareResponse(Exchange exchange);
    // Имя операции, для которой идет подготовка SOAP response
    String getOperationName();
}
