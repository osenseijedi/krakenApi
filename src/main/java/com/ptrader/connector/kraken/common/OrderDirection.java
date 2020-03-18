package com.ptrader.connector.kraken.common;

import com.fasterxml.jackson.annotation.JsonValue;

// FIXME : NEed to merge with input.TransactionType
public enum OrderDirection {

    BUY("buy"),
    SELL("sell");

    private String value;

    OrderDirection(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
