package com.ptrader.connector.kraken;

import com.ptrader.connector.ApiJsonExchange;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

class HttpJsonClient {

    static ApiJsonExchange executePublicQuery(ApiJsonExchange exchange, String baseUrl, Map<String, String> params) throws IOException {
        final StringBuilder sbUrl = new StringBuilder(baseUrl).append("?");

        if (params != null && !params.isEmpty()) {
            params.forEach((k, v) -> sbUrl.append(k).append("=").append(v).append("&"));

            exchange.getRequest().setParams(params);
        }

        URL url = new URL(sbUrl.toString());

        exchange.getRequest().setUrl(url);

        return getPublicJsonResponse(exchange, url);
    }


    private static ApiJsonExchange getPublicJsonResponse(ApiJsonExchange exchange, URL url) throws IOException {
        final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("GET");
            exchange.getRequest().setRequestMethod("GET");

            return getJsonResponse(exchange, connection);
        } finally {
            connection.disconnect();
        }
    }

    static ApiJsonExchange getJsonResponse(ApiJsonExchange exchange, HttpsURLConnection connection) throws IOException {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            exchange.getResponse().setRawResponse(response.toString());

            return exchange;
        }
    }
}
