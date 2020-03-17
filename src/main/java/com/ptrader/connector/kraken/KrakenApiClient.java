package com.ptrader.connector.kraken;

import com.ptrader.connector.ApiJsonExchange;
import com.ptrader.connector.kraken.input.AddStandardOrderInput;
import com.ptrader.connector.kraken.input.InfoInput;
import com.ptrader.connector.kraken.input.Interval;
import com.ptrader.connector.kraken.result.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class KrakenApiClient {

    private final KrakenApiClientInternal krakenApiClientInternal;

    public ApiJsonExchange getLastExchange() {
        return krakenApiClientInternal.getLastExchange();
    }

    // Constructor used when only public calls will be made (ie no need to pass apiKey)
    public KrakenApiClient() {
        this.krakenApiClientInternal = new KrakenApiClientInternal();
    }

    public KrakenApiClient(String apiKey, String apiSecret) {
        this.krakenApiClientInternal = new KrakenApiClientInternal(apiKey, apiSecret);
    }

    /* ***************************************************************************************************************** */
    /* ************ Public calls *************************************************************************************** */
    /* ***************************************************************************************************************** */

    // <editor-fold>
    public Optional<ServerTimeResult> getServerTime() {
        return this.krakenApiClientInternal.callPublic(KrakenApiMethod.SERVER_TIME, ServerTimeResult.class);
    }

    public Optional<AssetsInformationResult> getAssetsInformation() {
        return this.krakenApiClientInternal.callPublic(KrakenApiMethod.ASSET_INFORMATION, AssetsInformationResult.class);
    }

    public Optional<AssetsInformationResult> getAssetsInformation(String... assets) {

        Map<String, String> params = new HashMap<>();
        params.put("asset", String.join(",", assets));

        return this.krakenApiClientInternal.callPublic(KrakenApiMethod.ASSET_INFORMATION, AssetsInformationResult.class, params);
    }

    public Optional<AssetPairsResult> getAssetPairs() {
        return this.krakenApiClientInternal.callPublic(KrakenApiMethod.ASSET_PAIRS, AssetPairsResult.class);
    }

    public Optional<AssetPairsResult> getAssetPairs(InfoInput info, String... assetPairs) {

        Map<String, String> params = new HashMap<>();
        params.put("info", info.getValue());
        params.put("pair", String.join(",", assetPairs));

        return this.krakenApiClientInternal.callPublic(KrakenApiMethod.ASSET_PAIRS, AssetPairsResult.class, params);
    }


    public Optional<TickerInformationResult> getTickerInformation(List<String> pairs) {
        Map<String, String> params = new HashMap<>();
        params.put("pair", String.join(",", pairs));

        return this.krakenApiClientInternal.callPublic(KrakenApiMethod.TICKER_INFORMATION, TickerInformationResult.class, params);
    }

    public Optional<OHLCResult> getOHLC(String pair, Interval interval, Long sincelastId) {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("interval", String.valueOf(interval.getMinutes()));
        params.put("since", String.valueOf(sincelastId));

        return this.krakenApiClientInternal.callPublicWithLastId(KrakenApiMethod.OHLC, OHLCResult.class, params);
    }

    public Optional<OHLCResult> getOHLC(String pair, Interval interval) {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("interval", String.valueOf(interval.getMinutes()));

        return this.krakenApiClientInternal.callPublicWithLastId(KrakenApiMethod.OHLC, OHLCResult.class, params);
    }

    public Optional<OrderBookResult> getOrderBook(String pair, Integer count) {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("count", String.valueOf(count));

        return this.krakenApiClientInternal.callPublic(KrakenApiMethod.ORDER_BOOK, OrderBookResult.class, params);
    }

    public Optional<OrderBookResult> getOrderBook(String pair) {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);

        return this.krakenApiClientInternal.callPublic(KrakenApiMethod.ORDER_BOOK, OrderBookResult.class, params);
    }

    public Optional<RecentTradeResult> getRecentTrades(String pair) {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);

        return this.krakenApiClientInternal.callPublicWithLastId(KrakenApiMethod.RECENT_TRADES, RecentTradeResult.class, params);
    }

    public Optional<RecentTradeResult> getRecentTrades(String pair, Long sincelastId) {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("since", String.valueOf(sincelastId));

        return this.krakenApiClientInternal.callPublicWithLastId(KrakenApiMethod.RECENT_TRADES, RecentTradeResult.class, params);
    }

    public Optional<RecentSpreadResult> getRecentSpreads(String pair) {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);

        return this.krakenApiClientInternal.callPublicWithLastId(KrakenApiMethod.RECENT_SPREADS, RecentSpreadResult.class, params);
    }

    public Optional<RecentSpreadResult> getRecentSpreads(String pair, Long sincelastId) {

        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("since", String.valueOf(sincelastId));

        return this.krakenApiClientInternal.callPublicWithLastId(KrakenApiMethod.RECENT_SPREADS, RecentSpreadResult.class, params);
    }
    // </editor-fold>

    /* ***************************************************************************************************************** */
    /* ************ Private calls ************************************************************************************** */
    /* ***************************************************************************************************************** */

    // <editor-fold>
    public Optional<AccountBalanceResult> getAccountBalance() {
        return this.krakenApiClientInternal.callPrivate(KrakenApiMethod.ACCOUNT_BALANCE, AccountBalanceResult.class);
    }

    public Optional<TradeBalanceResult> getTradeBalance() {
        return this.krakenApiClientInternal.callPrivate(KrakenApiMethod.TRADE_BALANCE, TradeBalanceResult.class);
    }

    public Optional<OpenOrdersResult> getOpenOrders() {
        return this.krakenApiClientInternal.callPrivate(KrakenApiMethod.OPEN_ORDERS, OpenOrdersResult.class);
    }

    public Optional<ClosedOrdersResult> getClosedOrders() {
        return this.krakenApiClientInternal.callPrivate(KrakenApiMethod.CLOSED_ORDERS, ClosedOrdersResult.class);
    }

    public Optional<OrdersInformationResult> getOrdersInformation(List<String> transactions) {

        Map<String, String> params = new HashMap<>();
        params.put("txid", String.join(",", transactions));

        return this.krakenApiClientInternal.callPrivate(KrakenApiMethod.ORDERS_INFORMATION, OrdersInformationResult.class, params);
    }

    public Optional<TradesHistoryResult> getTradesHistory() {
        return this.krakenApiClientInternal.callPrivate(KrakenApiMethod.TRADES_HISTORY, TradesHistoryResult.class);
    }

    public Optional<TradesInformationResult> getTradesInformation(List<String> transactions) {

        Map<String, String> params = new HashMap<>();
        params.put("txid", String.join(",", transactions));

        return this.krakenApiClientInternal.callPrivate(KrakenApiMethod.TRADES_INFORMATION, TradesInformationResult.class, params);
    }

    public Optional<OpenPositionsResult> getOpenPositions(List<String> transactions) {

        Map<String, String> params = new HashMap<>();
        params.put("txid", String.join(",", transactions));

        return this.krakenApiClientInternal.callPrivate(KrakenApiMethod.OPEN_POSITIONS, OpenPositionsResult.class, params);
    }

    public Optional<LedgersInformationResult> getLedgersInformation() {
        return this.krakenApiClientInternal.callPrivate(KrakenApiMethod.LEDGERS_INFORMATION, LedgersInformationResult.class);
    }

    public Optional<LedgersResult> getLedgers(List<String> ledgerIds) {

        Map<String, String> params = new HashMap<>();
        params.put("id", String.join(",", ledgerIds));

        return this.krakenApiClientInternal.callPrivate(KrakenApiMethod.QUERY_LEDGERS, LedgersResult.class, params);
    }

    public Optional<TradeVolumeResult> getTradeVolume() {
        return this.krakenApiClientInternal.callPrivate(KrakenApiMethod.TRADE_VOLUME, TradeVolumeResult.class);
    }

    public Optional<AddStandardOrderResult> addStandardOrder(AddStandardOrderInput input) {
        return this.krakenApiClientInternal.callPrivate(KrakenApiMethod.ADD_STANDARD_ORDER, AddStandardOrderResult.class, input.getInput());
    }
    //</editor-fold>
}
