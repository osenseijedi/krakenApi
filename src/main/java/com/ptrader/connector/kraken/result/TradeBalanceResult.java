package com.ptrader.connector.kraken.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptrader.connector.kraken.utils.JSONUtils;

import java.math.BigDecimal;

public class TradeBalanceResult extends Result<TradeBalanceResult> {

    @JsonProperty("eb")
    public BigDecimal equivalentBalance;

    @JsonProperty("tb")
    public BigDecimal tradeBalance;

    @JsonProperty("m")
    public BigDecimal marginAmount;

    @JsonProperty("n")
    public BigDecimal unrealizedNetProfitLoss;

    @JsonProperty("c")
    public BigDecimal costBasis;

    @JsonProperty("v")
    public BigDecimal floatingValuation;

    @JsonProperty("e")
    public BigDecimal equity;

    @JsonProperty("mf")
    public BigDecimal freeMargin;

    @JsonProperty("ml")
    public BigDecimal marginLevel;

    @Override
    public String toString() {
        return JSONUtils.toString(this);
    }
}
