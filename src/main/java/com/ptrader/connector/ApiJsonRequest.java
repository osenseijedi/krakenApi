package com.ptrader.connector;

import com.ptrader.connector.kraken.utils.JSONUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ApiJsonRequest {

    private URL url;

    private String requestMethod;
    private String nonce;
    private String signature;

    private ApiJsonRequestType requestType;

    private Map<String, String> params = new HashMap<>();
    private Map<String, String> requestProperties = new HashMap<>(); // headers ?

    @Override
    public String toString() {
        return JSONUtils.toString(this);
    }
}
