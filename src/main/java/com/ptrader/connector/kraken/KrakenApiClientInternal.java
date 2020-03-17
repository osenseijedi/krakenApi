package com.ptrader.connector.kraken;


import com.ptrader.connector.ApiJsonExchange;
import com.ptrader.connector.ApiJsonRequestType;
import com.ptrader.connector.kraken.result.Result;
import com.ptrader.connector.kraken.result.ResultWithLastId;
import com.ptrader.connector.kraken.utils.JSONUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class KrakenApiClientInternal {
    private String apiKey;
    private String apiSecret;

    private ApiJsonExchange lastExchange = new ApiJsonExchange();

    ApiJsonExchange getLastExchange() {
        return lastExchange;
    }

    KrakenApiClientInternal() {
    }

    KrakenApiClientInternal(String apiKey, String apiSecret) {
        this();
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    /* ***************************************************************************************************************** */
    /* ***************************************************************************************************************** */
    /* ***************************************************************************************************************** */

    private <R extends Result> Optional<R> __callPublic(KrakenApiMethod method,
                                                          Class<R> resultClass,
                                                          Map<String, String> params,
                                                          BiFunction<ApiJsonExchange, Class<R>, R> makeResult) {
        try {
            lastExchange = KrakenHttpJsonClient.executePublicQuery(
                    prepareExchange(ApiJsonRequestType.PUBLIC, method, resultClass),
                    method,
                    params
            );
            return Optional.ofNullable(checkResult(makeResult.apply(lastExchange, resultClass)));
        } catch (Exception ex) {
            handleException(ex);
        }

        return Optional.empty();
    }

    private <R extends Result> Optional<R> __callPrivate(KrakenApiMethod method,
                                                           Class<R> resultClass,
                                                           Map<String, String> params,
                                                           BiFunction<ApiJsonExchange, Class<R>, R> makeResult) {
        try {
            lastExchange = KrakenHttpJsonClient.executePrivateQuery(
                    prepareExchange(ApiJsonRequestType.PRIVATE, method, resultClass),
                    method,
                    apiKey,
                    apiSecret,
                    params
            );

            return Optional.ofNullable(makeResult.apply(lastExchange, resultClass));
        } catch (Exception ex) {
            handleException(ex);
        }

        return Optional.empty();
    }

    /* ***************************************************************************************************************** */
    /* ***************************************************************************************************************** */
    /* ***************************************************************************************************************** */

    <R extends Result> Optional<R> callPublic(KrakenApiMethod method, Class<R> resultClass) {
        return callPublic(method, resultClass, null);
    }

    <R extends Result> Optional<R> callPublic(KrakenApiMethod method, Class<R> resultClass, Map<String, String> params) {
        return __callPublic(method, resultClass, params, this::makeResult);
    }

    <R extends Result> Optional<R> callPublicWithLastId(KrakenApiMethod method, Class<R> resultClass, Map<String, String> params) {
        return __callPublic(method, resultClass, params, this::makeResultExtractLastId);
    }

    /* ***************************************************************************************************************** */
    /* ***************************************************************************************************************** */

    <R extends Result> Optional<R> callPrivate(KrakenApiMethod method, Class<R> resultClass) {
        return callPrivate(method, resultClass, null);
    }

    <R extends Result> Optional<R> callPrivate(KrakenApiMethod method, Class<R> resultClass, Map<String, String> params) {
        return __callPrivate(method, resultClass, params, this::makeResult);
    }

    /* ***************************************************************************************************************** */
    /* ***************************************************************************************************************** */
    /* ***************************************************************************************************************** */

    private <R extends Result> ApiJsonExchange prepareExchange(ApiJsonRequestType requestType, KrakenApiMethod method, Class<R> resultClass) {
        lastExchange = new ApiJsonExchange();
        lastExchange.setInitiatedOn(new Date().getTime());

        lastExchange.getMetadata().put("apiClient", this.getClass());
        lastExchange.getMetadata().put("resultClass", resultClass);
        lastExchange.getMetadata().put("method", method);
        lastExchange.getMetadata().put("requestType", requestType);

        return lastExchange;
    }

    private void handleException(Exception ex) {
        lastExchange.setException(ex);
        lastExchange.setCompletedOn(new Date().getTime());
    }

    private <R extends Result> R makeResult(String rawResponse, Class<R> resultClass) {
        try {
            return JSONUtils.fromJsonStringToObject(rawResponse, resultClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <R extends Result> R makeResult(ApiJsonExchange exchange, Class<R> resultClass) {
        return makeResult(exchange.getResponse().getRawResponse(), resultClass);
    }

    private <R extends Result> R makeResultExtractLastId(ApiJsonExchange exchange, Class<R> resultClass) {
        LastIdExtractedResult extractedResult = extractLastId(exchange.getResponse().getRawResponse());

        R res = makeResult(extractedResult.responseWithoutLastId, resultClass);
        ((ResultWithLastId) res).setLastId(extractedResult.lastId);

        return res;
    }

    private <R extends Result> R checkResult(R res) throws KrakenApiException {
        if (!res.getError().isEmpty()) {
            throw new KrakenApiException(res.getError());
        }
        lastExchange.setCompletedOn(new Date().getTime());
        return res;
    }


    /* ***************************************************************************************************************** */
    /* ***************************************************************************************************************** */
    /* ***************************************************************************************************************** */

    /**
     * LastId extracted class result
     */
    private static class LastIdExtractedResult {
        String responseWithoutLastId;
        Long lastId;

        LastIdExtractedResult(String responseWithoutLastId, Long lastId) {
            this.responseWithoutLastId = responseWithoutLastId;
            this.lastId = lastId;
        }
    }

    /**
     * Extract last id from string and wrap it with response without it
     *
     * @return extracted result
     */
    private LastIdExtractedResult extractLastId(String response) {
        final String lastPattern = ",(\\s*)\"last\":\"{0,1}([0-9]+)\"{0,1}";

        Pattern pattern = Pattern.compile(lastPattern);
        Matcher matcher = pattern.matcher(response);

        if (matcher.find()) {
            response = response.replaceAll(lastPattern, "");

            return new LastIdExtractedResult(response, Long.valueOf(matcher.group(2)));
        } else {
            throw new RuntimeException("unable to extract last id : " + response);
        }
    }

}
