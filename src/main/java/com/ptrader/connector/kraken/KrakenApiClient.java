package com.ptrader.connector.kraken;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptrader.connector.ApiJsonExchange;
import com.ptrader.connector.ApiJsonRequestType;
import com.ptrader.connector.kraken.input.InfoInput;
import com.ptrader.connector.kraken.input.Interval;
import com.ptrader.connector.kraken.result.*;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KrakenApiClient {

    private String apiKey;
    private String apiSecret;

    private ApiJsonExchange lastExchange = new ApiJsonExchange();

    public ApiJsonExchange getLastExchange() {
        return lastExchange;
    }

    public KrakenApiClient() {
    }

    public KrakenApiClient(String apiKey, String apiSecret) {
        this();
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }


    protected <T extends Result> T callPublic(KrakenApiMethod method, Class<T> resultClass) throws KrakenApiException {
        return callPublic(method, resultClass, null);
    }

    protected <T extends Result> T callPublic(KrakenApiMethod method, Class<T> resultClass, Map<String, String> params) throws KrakenApiException {
        try {
            lastExchange = KrakenHttpJsonClient.executePublicQuery(prepareExchange(ApiJsonRequestType.PUBLIC, method, resultClass), method, params);
            return checkResult(makeResult(lastExchange, resultClass));
        } catch (Exception ex) {
            throw handleException(ex);
        }
    }

    protected <T extends Result> T callPublicWithLastId(KrakenApiMethod method, Class<T> resultClass, Map<String, String> params) throws KrakenApiException {
        try {
            lastExchange = KrakenHttpJsonClient.executePublicQuery(prepareExchange(ApiJsonRequestType.PUBLIC, method, resultClass), method, params);
            return checkResult(makeResultExtractLastId(lastExchange, resultClass));
        } catch (Exception ex) {
            throw handleException(ex);
        }
    }


    protected <T extends Result> T callPrivate(KrakenApiMethod method, Class<T> resultClass) throws KrakenApiException {
        return callPrivate(method, resultClass, null);
    }


    protected <T extends Result> T callPrivate(KrakenApiMethod method, Class<T> resultClass, Map<String, String> params) throws KrakenApiException {
        try {
            ApiJsonExchange exchange = KrakenHttpJsonClient.executePrivateQuery(
                    prepareExchange(ApiJsonRequestType.PRIVATE, method, resultClass),
                    method,
                    apiKey,
                    apiSecret,
                    params
            );

            return makeResult(exchange, resultClass);
        } catch (Exception ex) {
            throw handleException(ex);
        }
    }


    private <T extends Result> ApiJsonExchange prepareExchange(ApiJsonRequestType requestType, KrakenApiMethod method, Class<T> resultClass) {
        lastExchange = new ApiJsonExchange();
        lastExchange.setInitiatedOn(new Date().getTime());

        lastExchange.getMetadata().put("apiClient", this.getClass());
        lastExchange.getMetadata().put("resultClass", resultClass);
        lastExchange.getMetadata().put("method", method);
        lastExchange.getMetadata().put("requestType", requestType);

        return lastExchange;
    }

    private KrakenApiException handleException(Exception ex) {
        lastExchange.setException(ex);
        lastExchange.setCompletedOn(new Date().getTime());
        return new KrakenApiException("unable to query Kraken API", ex);
    }


    private <T extends Result> T makeResult(String rawResponse, Class<T> resultClass) {
        try {
            return new ObjectMapper().readValue(rawResponse, resultClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private <T extends Result> T makeResult(ApiJsonExchange exchange, Class<T> resultClass) {
        return makeResult(exchange.getResponse().getRawResponse(), resultClass);
    }

    private <T extends Result> T makeResultExtractLastId(ApiJsonExchange exchange, Class<T> resultClass) {
        LastIdExtractedResult extractedResult = extractLastId(exchange.getResponse().getRawResponse());

        T res = makeResult(extractedResult.responseWithoutLastId, resultClass);
        ((ResultWithLastId) res).setLastId(extractedResult.lastId);

        return res;
    }

    private <T extends Result> T checkResult(T res) throws KrakenApiException {
        if (!res.getError().isEmpty()) {
            throw new KrakenApiException(res.getError());
        }
        lastExchange.setCompletedOn(new Date().getTime());
        return res;
    }


    /*******************************************************************************************************************/
    /*******************************************************************************************************************/
    /*******************************************************************************************************************/

    /**
     * LastId extracted class result
     */
    private static class LastIdExtractedResult {
        public String responseWithoutLastId;
        public Long lastId;

        public LastIdExtractedResult(String responseWithoutLastId, Long lastId) {
            this.responseWithoutLastId = responseWithoutLastId;
            this.lastId = lastId;
        }
    }

    /**
     * Extract last id from string and wrap it with response without it
     *
     * @param response
     * @return extracted result
     * @throws KrakenApiException
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


    /*******************************************************************************************************************/
    /*******************************************************************************************************************/
    /*******************************************************************************************************************/

    public ServerTimeResult getServerTime() throws KrakenApiException {
        return callPublic(KrakenApiMethod.SERVER_TIME, ServerTimeResult.class);
    }

    public AssetsInformationResult getAssetsInformation() throws KrakenApiException {
        return callPublic(KrakenApiMethod.ASSET_INFORMATION, AssetsInformationResult.class);
    }

    public AssetsInformationResult getAssetsInformation(String... assets) throws KrakenApiException {

        Map<String, String> params = new HashMap<>();
        params.put("asset", String.join(",", assets));

        return callPublic(KrakenApiMethod.ASSET_INFORMATION, AssetsInformationResult.class, params);
    }

    public AssetPairsResult getAssetPairs() throws KrakenApiException {
        return callPublic(KrakenApiMethod.ASSET_PAIRS, AssetPairsResult.class);
    }

    public AssetPairsResult getAssetPairs(InfoInput info, String... assetPairs) throws KrakenApiException {

        Map<String, String> params = new HashMap<>();
        params.put("info", info.getValue());
        params.put("pair", String.join(",", assetPairs));

        return callPublic(KrakenApiMethod.ASSET_PAIRS, AssetPairsResult.class, params);
    }


    public TickerInformationResult getTickerInformation(List<String> pairs) throws KrakenApiException {
        Map<String, String> params = new HashMap<>();
        params.put("pair", String.join(",", pairs));

        return callPublic(KrakenApiMethod.TICKER_INFORMATION, TickerInformationResult.class, params);
    }

    public OHLCResult getOHLC(String pair, Interval interval, Integer since) throws KrakenApiException {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("interval", String.valueOf(interval.getMinutes()));
        params.put("since", String.valueOf(since));

        return callPublicWithLastId(KrakenApiMethod.OHLC, OHLCResult.class, params);
    }

    public OHLCResult getOHLC(String pair, Interval interval) throws KrakenApiException {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("interval", String.valueOf(interval.getMinutes()));

        return callPublicWithLastId(KrakenApiMethod.OHLC, OHLCResult.class, params);
    }

    public OrderBookResult getOrderBook(String pair, Integer count) throws KrakenApiException {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("count", String.valueOf(count));

        return callPublic(KrakenApiMethod.ORDER_BOOK, OrderBookResult.class, params);
    }

    public OrderBookResult getOrderBook(String pair) throws KrakenApiException {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);

        return callPublic(KrakenApiMethod.ORDER_BOOK, OrderBookResult.class, params);
    }

    public RecentTradeResult getRecentTrades(String pair) throws KrakenApiException {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);

        return callPublicWithLastId(KrakenApiMethod.RECENT_TRADES, RecentTradeResult.class, params);
    }

    public RecentTradeResult getRecentTrades(String pair, Integer since) throws KrakenApiException {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("since", String.valueOf(since));

        return callPublicWithLastId(KrakenApiMethod.RECENT_TRADES, RecentTradeResult.class, params);
    }

    public RecentSpreadResult getRecentSpreads(String pair) throws KrakenApiException {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);

        return callPublicWithLastId(KrakenApiMethod.RECENT_SPREADS, RecentSpreadResult.class, params);
    }

    public RecentSpreadResult getRecentSpreads(String pair, Integer since) throws KrakenApiException {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("since", String.valueOf(since));

        return callPublicWithLastId(KrakenApiMethod.RECENT_SPREADS, RecentSpreadResult.class, params);
    }

    public AccountBalanceResult getAccountBalance() throws KrakenApiException {
        return callPrivate(KrakenApiMethod.ACCOUNT_BALANCE, AccountBalanceResult.class);
    }

    public TradeBalanceResult getTradeBalance() throws KrakenApiException {
        return callPrivate(KrakenApiMethod.TRADE_BALANCE, TradeBalanceResult.class);
    }

    public OpenOrdersResult getOpenOrders() throws KrakenApiException {
        return callPrivate(KrakenApiMethod.OPEN_ORDERS, OpenOrdersResult.class);
    }

    public ClosedOrdersResult getClosedOrders() throws KrakenApiException {
        return callPrivate(KrakenApiMethod.CLOSED_ORDERS, ClosedOrdersResult.class);
    }

    public OrdersInformationResult getOrdersInformation(List<String> transactions) throws KrakenApiException {

        Map<String, String> params = new HashMap<>();
        params.put("txid", transactions.stream().collect(Collectors.joining(",")));

        return callPrivate(KrakenApiMethod.ORDERS_INFORMATION, OrdersInformationResult.class, params);
    }

    public TradesHistoryResult getTradesHistory() throws KrakenApiException {
        return callPrivate(KrakenApiMethod.TRADES_HISTORY, TradesHistoryResult.class);
    }

    public TradesInformationResult getTradesInformation(List<String> transactions) throws KrakenApiException {

        Map<String, String> params = new HashMap<>();
        params.put("txid", transactions.stream().collect(Collectors.joining(",")));

        return callPrivate(KrakenApiMethod.TRADES_INFORMATION, TradesInformationResult.class, params);
    }

    public OpenPositionsResult getOpenPositions(List<String> transactions) throws KrakenApiException {

        Map<String, String> params = new HashMap<>();
        params.put("txid", transactions.stream().collect(Collectors.joining(",")));

        return callPrivate(KrakenApiMethod.OPEN_POSITIONS, OpenPositionsResult.class, params);
    }

    public LedgersInformationResult getLedgersInformation() throws KrakenApiException {
        return callPrivate(KrakenApiMethod.LEDGERS_INFORMATION, LedgersInformationResult.class);
    }

    public LedgersResult getLedgers(List<String> ledgerIds) throws KrakenApiException {

        Map<String, String> params = new HashMap<>();
        params.put("id", ledgerIds.stream().collect(Collectors.joining(",")));

        return callPrivate(KrakenApiMethod.QUERY_LEDGERS, LedgersResult.class, params);
    }

    public TradeVolumeResult getTradeVolume() throws KrakenApiException {
        return callPrivate(KrakenApiMethod.TRADE_VOLUME, TradeVolumeResult.class);
    }

}
