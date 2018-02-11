package com.ptrader.connector.kraken;

public enum KrakenApiMethod {

    SERVER_TIME("Time", false),
    ASSET_INFORMATION("Assets", false),
    ASSET_PAIRS("AssetPairs", false),
    TICKER_INFORMATION("Ticker", false),
    OHLC("OHLC", false),
    ORDER_BOOK("Depth", false),
    RECENT_TRADES("Trades", false),
    RECENT_SPREADS("Spread", false),

    ACCOUNT_BALANCE("Balance", true),
    TRADE_BALANCE("TradeBalance", true),
    OPEN_ORDERS("OpenOrders", true),
    CLOSED_ORDERS("ClosedOrders", true),
    ORDERS_INFORMATION("QueryOrders", true),
    TRADES_HISTORY("TradesHistory", true),
    TRADES_INFORMATION("QueryTrades", true),
    OPEN_POSITIONS("OpenPositions", true),
    LEDGERS_INFORMATION("Ledgers", true),
    QUERY_LEDGERS("QueryLedgers", true),
    TRADE_VOLUME("TradeVolume", true);

    //TODO put in a properties
    public final static String BASE_URL = "https://api.kraken.com";
    private final static int apiVersion = 0;

    private String url;
    private boolean isPrivate;

    KrakenApiMethod(String url, boolean isPrivate) {
        this.url = url;
        this.isPrivate = isPrivate;
    }

    String getEntireUrl() {
        return BASE_URL + getUriPath();
    }

    String getUriPath() {
        return "/" + apiVersion + "/" + (isPrivate ? "private/" : "public/") + url;
    }
}
