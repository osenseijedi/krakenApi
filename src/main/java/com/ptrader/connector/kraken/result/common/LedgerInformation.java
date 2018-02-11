package com.ptrader.connector.kraken.result.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptrader.connector.kraken.utils.JSONUtils;

import java.math.BigDecimal;

public class LedgerInformation {

    @JsonProperty("refid")
    public String referenceId;

    @JsonProperty("time")
    public Long timestamp;

    public String type;

    @JsonProperty("aclass")
    public String assetClass;

    public String asset;

    public BigDecimal amount;

    public BigDecimal fee;

    public BigDecimal balance;

    @Override
    public String toString() {
        return JSONUtils.toString(this);
    }
}
