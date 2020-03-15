package com.ptrader.connector.kraken.input;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderFlags {

    VOLUME_IN_QUOTE_CURRENCY("viqc"), // (not available for leveraged orders)
    PREFER_FEE_IN_BASE_CURRENCY("fcib "),
    PREFER_FEE_IN_QUOTE_CURRENCY("fciq"),
    NO_MARKET_PRICE_PROTECTION("nompp"),
    POST_ONLY_ORDER("post"); // (available when ordertype = limit)

    private String value;

    OrderFlags(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
