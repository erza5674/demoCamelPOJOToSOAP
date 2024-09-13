package com.example.demoCamelPOJOToSOAP.extensions;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformerV2;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;

import static org.apache.http.HttpHeaders.CONNECTION;

public class NoKeepAliveTransformer  implements ResponseDefinitionTransformerV2 {
    @Override
    public String getName() {
        return "keep-alive-disabler";
    }

    @Override
    public ResponseDefinition transform(ServeEvent serveEvent) {
        return ResponseDefinitionBuilder
                .like(serveEvent.getResponseDefinition())
                .withHeader(CONNECTION, "close")
                .build();
    }
}
