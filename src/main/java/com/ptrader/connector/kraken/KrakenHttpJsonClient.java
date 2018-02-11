package com.ptrader.connector.kraken;

import com.ptrader.connector.ApiJsonExchange;
import com.ptrader.connector.kraken.utils.KrakenUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Map;


public class KrakenHttpJsonClient extends HttpJsonClient {

    private static void checkApiKey(String apiKey) throws KrakenApiException {
        if (apiKey == null) {
            throw new KrakenApiException("must provide API key and secret");
        }
    }
    private static void checkKeys(String apiKey, String secret) throws KrakenApiException {
        checkApiKey(apiKey);

        if (secret == null) {
            throw new KrakenApiException("must provide API key and secret");
        }
    }

    protected static ApiJsonExchange getPrivateJsonResponse(ApiJsonExchange exchange, URL url, String apiKey, String postData, String signature) throws IOException, KrakenApiException {

        checkApiKey(apiKey);

        HttpsURLConnection connection = null;
        try {
            exchange.getRequest().setUrl(url);
            exchange.getRequest().setRequestMethod("POST");
            exchange.getRequest().getRequestProperties().put("API-Key", apiKey);
            exchange.getRequest().getRequestProperties().put("API-Sign", signature);

            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("API-Key", apiKey);
            connection.addRequestProperty("API-Sign", signature);

            if (postData != null && !postData.isEmpty()) {
                connection.setDoOutput(true);
                try (OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream())) {
                    out.write(postData);
                }
            }

            return getJsonResponse(exchange, connection);
        } finally {
            connection.disconnect();
        }
    }

    protected static ApiJsonExchange executePublicQuery(ApiJsonExchange exchange, KrakenApiMethod method, Map<String, String> params) throws IOException {
        return executePublicQuery(exchange, method.getEntireUrl(), params);
    }

    public static ApiJsonExchange executePrivateQuery(ApiJsonExchange exchange, KrakenApiMethod method, String apiKey, String secret, Map<String, String> params) throws IOException, KrakenApiException {
        checkKeys(apiKey, secret);

        String nonce = KrakenUtils.generateNonce();

        exchange.getRequest().setNonce(nonce);

        StringBuilder sbPostData = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            params.forEach((k, v) -> sbPostData.append(k).append("=").append(v).append("&"));
            exchange.getRequest().setParams(params);
        }
        sbPostData.append("nonce=").append(nonce);


        String postData = sbPostData.toString();

        String signature = KrakenUtils.generateSignature(method.getUriPath(), nonce, secret, postData);
        exchange.getRequest().setSignature(signature);

        return getPrivateJsonResponse(exchange, new URL(method.getEntireUrl()), apiKey, postData, signature);
    }

}
