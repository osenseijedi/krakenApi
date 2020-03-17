package com.ptrader.connector;

import com.ptrader.connector.kraken.utils.JSONUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
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

    @Override
    public String toString() {
        return JSONUtils.toString(this);
    }
}
