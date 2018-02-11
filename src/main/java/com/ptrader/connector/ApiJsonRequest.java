package com.ptrader.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ApiJsonRequest {

    private URL url;

    private String requestMethod;
    private String nonce;
    private String signature;

    private ApiJsonRequestType requestType;

    private Map<String, String> params = new HashMap<>();
    private Map<String, String> requestProperties = new HashMap<>(); // headers ?


    public ApiJsonRequest() {

    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Map<String, String> getRequestProperties() {
        return requestProperties;
    }

    public void setRequestProperties(Map<String, String> requestProperties) {
        this.requestProperties = requestProperties;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public ApiJsonRequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(ApiJsonRequestType requestType) {
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
