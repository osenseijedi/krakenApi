package com.ptrader.connector.kraken.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptrader.connector.kraken.result.common.OrderDirection;
import com.ptrader.connector.kraken.result.common.OrderType;
import com.ptrader.connector.kraken.utils.JSONUtils;

import java.math.BigDecimal;
import java.util.Map;

public class TradesInformationResult extends Result<Map<String, TradesInformationResult.TradeInformation>> {

    public static class TradeInformation {

        @JsonProperty("ordertxid")
        public String orderTransactionId;

        @JsonProperty("pair")
        public String assetPair;

        @JsonProperty("time")
        public Long tradeTimestamp;

        @JsonProperty("type")
        public OrderDirection orderDirection;

        @JsonProperty("ordertype")
        public OrderType orderType;

        public BigDecimal price;

        public BigDecimal cost;

        public BigDecimal fee;

        @JsonProperty("vol")
        public BigDecimal volume;

        public BigDecimal margin;

        @JsonProperty("misc")
        public String miscellaneous;

        @Override
        public String toString() {
            return JSONUtils.toString(this);
        }
    }
}
