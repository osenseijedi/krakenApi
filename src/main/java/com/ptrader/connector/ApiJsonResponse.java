package com.ptrader.connector;

import com.ptrader.connector.kraken.utils.JSONUtils;

public class ApiJsonResponse {

    private String rawResponse;

    public ApiJsonResponse() {
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    @Override
    public String toString() {
        return JSONUtils.toString(this);
    }

}
