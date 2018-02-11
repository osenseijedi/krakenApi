package com.ptrader.connector;

import com.ptrader.connector.kraken.utils.JSONUtils;

import java.util.HashMap;
import java.util.Map;

public class ApiJsonExchange {

    private Map<String, Object> metadata = new HashMap<>();

    private final ApiJsonRequest request;

    private final ApiJsonResponse response;

    private long initiatedOn;
    private long completedOn;

    private Exception exception;

    public ApiJsonExchange(){
        request = new ApiJsonRequest();
        response = new ApiJsonResponse();
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public ApiJsonRequest getRequest() {
        return request;
    }

    public ApiJsonResponse getResponse() {
        return response;
    }

    public long getInitiatedOn() {
        return initiatedOn;
    }

    public void setInitiatedOn(long initiatedOn) {
        this.initiatedOn = initiatedOn;
    }

    public long getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(long completedOn) {
        this.completedOn = completedOn;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return JSONUtils.toString(this);
    }
}
