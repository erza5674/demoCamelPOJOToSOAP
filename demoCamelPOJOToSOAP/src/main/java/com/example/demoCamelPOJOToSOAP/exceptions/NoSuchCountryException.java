package com.example.demoCamelPOJOToSOAP.exceptions;

import jakarta.xml.ws.WebFault;

@WebFault(name = "NoSuchCountry")
public class NoSuchCountryException extends Exception {
    private static final long serialVersionUID = 1L;

    private String faultInfo;

    public NoSuchCountryException() {
    }

    public NoSuchCountryException(String message) {
        super(message);
    }

    public NoSuchCountryException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchCountryException(Throwable cause) {
        super(cause);
    }

    public NoSuchCountryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
