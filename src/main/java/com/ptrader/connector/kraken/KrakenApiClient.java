package com.ptrader.connector.kraken;

import com.ptrader.connector.ApiJsonExchange;
import com.ptrader.connector.ApiJsonRequestType;
import com.ptrader.connector.kraken.input.InfoInput;
import com.ptrader.connector.kraken.input.Interval;
import com.ptrader.connector.kraken.result.*;
import com.ptrader.connector.kraken.utils.JSONUtils;

import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;
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

    public KrakenApiClient() {}

    public KrakenApiClient(String apiKey, String apiSecret) {
        this();
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    /*******************************************************************************************************************/
    /*******************************************************************************************************************/
    /*******************************************************************************************************************/

    protected <T extends Result> Optional<T> __callPublic(KrakenApiMethod method, Class<T> resultClass, Map<String, String> params, BiFunction<ApiJsonExchange, Class<T>, T> makeResult) {
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

    protected <T extends Result> Optional<T> __callPrivate(KrakenApiMethod method, Class<T> resultClass, Map<String, String> params, BiFunction<ApiJsonExchange, Class<T>, T> makeResult) {
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

    /*******************************************************************************************************************/
    /*******************************************************************************************************************/
    /*******************************************************************************************************************/

    protected <T extends Result> Optional<T> callPublic(KrakenApiMethod method, Class<T> resultClass) {
        return callPublic(method, resultClass, null);
    }

    protected <T extends Result> Optional<T> callPublic(KrakenApiMethod method, Class<T> resultClass, Map<String, String> params) {
        return __callPublic(method, resultClass, params, this::makeResult);
    }

    protected <T extends Result> Optional<T> callPublicWithLastId(KrakenApiMethod method, Class<T> resultClass, Map<String, String> params) {
        return __callPublic(method, resultClass, params, this::makeResultExtractLastId);
    }

    /*******************************************************************************************************************/
    /*******************************************************************************************************************/

    protected <T extends Result> Optional<T> callPrivate(KrakenApiMethod method, Class<T> resultClass) {
        return callPrivate(method, resultClass, null);
    }

    protected <T extends Result> Optional<T> callPrivate(KrakenApiMethod method, Class<T> resultClass, Map<String, String> params) {
        return __callPrivate(method, resultClass, params, this::makeResult);
    }

    /*******************************************************************************************************************/
    /*******************************************************************************************************************/
    /*******************************************************************************************************************/

    private <T extends Result> ApiJsonExchange prepareExchange(ApiJsonRequestType requestType, KrakenApiMethod method, Class<T> resultClass) {
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

    private <T extends Result> T makeResult(String rawResponse, Class<T> resultClass) {
        try {
            return JSONUtils.fromJsonStringToObject(rawResponse, resultClass);
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

    public Optional<ServerTimeResult> getServerTime() {
        return callPublic(KrakenApiMethod.SERVER_TIME, ServerTimeResult.class);
    }

    public Optional<AssetsInformationResult> getAssetsInformation() {
        return callPublic(KrakenApiMethod.ASSET_INFORMATION, AssetsInformationResult.class);
    }

    public Optional<AssetsInformationResult> getAssetsInformation(String... assets) {

        Map<String, String> params = new HashMap<>();
        params.put("asset", String.join(",", assets));

        return callPublic(KrakenApiMethod.ASSET_INFORMATION, AssetsInformationResult.class, params);
    }

    public Optional<AssetPairsResult> getAssetPairs() {
        return callPublic(KrakenApiMethod.ASSET_PAIRS, AssetPairsResult.class);
    }

    public Optional<AssetPairsResult> getAssetPairs(InfoInput info, String... assetPairs) {

        Map<String, String> params = new HashMap<>();
        params.put("info", info.getValue());
        params.put("pair", String.join(",", assetPairs));

        return callPublic(KrakenApiMethod.ASSET_PAIRS, AssetPairsResult.class, params);
    }


    public Optional<TickerInformationResult> getTickerInformation(List<String> pairs) {
        Map<String, String> params = new HashMap<>();
        params.put("pair", String.join(",", pairs));

        return callPublic(KrakenApiMethod.TICKER_INFORMATION, TickerInformationResult.class, params);
    }

    public Optional<OHLCResult> getOHLC(String pair, Interval interval, Integer since) {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("interval", String.valueOf(interval.getMinutes()));
        params.put("since", String.valueOf(since));

        return callPublicWithLastId(KrakenApiMethod.OHLC, OHLCResult.class, params);
    }

    public Optional<OHLCResult> getOHLC(String pair, Interval interval) {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("interval", String.valueOf(interval.getMinutes()));

        return callPublicWithLastId(KrakenApiMethod.OHLC, OHLCResult.class, params);
    }

    public Optional<OrderBookResult> getOrderBook(String pair, Integer count) {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("count", String.valueOf(count));

        return callPublic(KrakenApiMethod.ORDER_BOOK, OrderBookResult.class, params);
    }

    public Optional<OrderBookResult> getOrderBook(String pair) {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);

        return callPublic(KrakenApiMethod.ORDER_BOOK, OrderBookResult.class, params);
    }

    public Optional<RecentTradeResult> getRecentTrades(String pair) {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);

        return callPublicWithLastId(KrakenApiMethod.RECENT_TRADES, RecentTradeResult.class, params);
    }

    public Optional<RecentTradeResult> getRecentTrades(String pair, Integer since) {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("since", String.valueOf(since));

        return callPublicWithLastId(KrakenApiMethod.RECENT_TRADES, RecentTradeResult.class, params);
    }

    public Optional<RecentSpreadResult> getRecentSpreads(String pair) {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);

        return callPublicWithLastId(KrakenApiMethod.RECENT_SPREADS, RecentSpreadResult.class, params);
    }

    public Optional<RecentSpreadResult> getRecentSpreads(String pair, Integer since) {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("since", String.valueOf(since));

        return callPublicWithLastId(KrakenApiMethod.RECENT_SPREADS, RecentSpreadResult.class, params);
    }

    public Optional<AccountBalanceResult> getAccountBalance() {
        return callPrivate(KrakenApiMethod.ACCOUNT_BALANCE, AccountBalanceResult.class);
    }

    public Optional<TradeBalanceResult> getTradeBalance() {
        return callPrivate(KrakenApiMethod.TRADE_BALANCE, TradeBalanceResult.class);
    }

    public Optional<OpenOrdersResult> getOpenOrders() {
        return callPrivate(KrakenApiMethod.OPEN_ORDERS, OpenOrdersResult.class);
    }

    public Optional<ClosedOrdersResult> getClosedOrders() {
        return callPrivate(KrakenApiMethod.CLOSED_ORDERS, ClosedOrdersResult.class);
    }

    public Optional<OrdersInformationResult> getOrdersInformation(List<String> transactions) {

        Map<String, String> params = new HashMap<>();
        params.put("txid", transactions.stream().collect(Collectors.joining(",")));

        return callPrivate(KrakenApiMethod.ORDERS_INFORMATION, OrdersInformationResult.class, params);
    }

    public Optional<TradesHistoryResult> getTradesHistory() {
        return callPrivate(KrakenApiMethod.TRADES_HISTORY, TradesHistoryResult.class);
    }

    public Optional<TradesInformationResult> getTradesInformation(List<String> transactions) {

        Map<String, String> params = new HashMap<>();
        params.put("txid", transactions.stream().collect(Collectors.joining(",")));

        return callPrivate(KrakenApiMethod.TRADES_INFORMATION, TradesInformationResult.class, params);
    }

    public Optional<OpenPositionsResult> getOpenPositions(List<String> transactions) {

        Map<String, String> params = new HashMap<>();
        params.put("txid", transactions.stream().collect(Collectors.joining(",")));

        return callPrivate(KrakenApiMethod.OPEN_POSITIONS, OpenPositionsResult.class, params);
    }

    public Optional<LedgersInformationResult> getLedgersInformation() {
        return callPrivate(KrakenApiMethod.LEDGERS_INFORMATION, LedgersInformationResult.class);
    }

    public Optional<LedgersResult> getLedgers(List<String> ledgerIds) {

        Map<String, String> params = new HashMap<>();
        params.put("id", ledgerIds.stream().collect(Collectors.joining(",")));

        return callPrivate(KrakenApiMethod.QUERY_LEDGERS, LedgersResult.class, params);
    }

    public Optional<TradeVolumeResult> getTradeVolume() {
        return callPrivate(KrakenApiMethod.TRADE_VOLUME, TradeVolumeResult.class);
    }

}
