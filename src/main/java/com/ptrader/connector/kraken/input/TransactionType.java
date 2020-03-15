package com.ptrader.connector.kraken.input;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {

    BUY("buy"),
    SELL("sell");

    private String value;

    TransactionType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
