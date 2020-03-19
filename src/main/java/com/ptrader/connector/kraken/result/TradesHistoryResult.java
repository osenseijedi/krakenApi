package com.ptrader.connector.kraken.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptrader.connector.kraken.common.OrderDirection;
import com.ptrader.connector.kraken.common.OrderType;
import com.ptrader.connector.kraken.utils.JSONUtils;

import java.math.BigDecimal;
import java.util.Map;

public class TradesHistoryResult extends Result<TradesHistoryResult.TradesHistory> {

    public static class TradesHistory {

        @JsonProperty("trades")
        public Map<String, TradeHistory> trades;

        public Long count;

        @Override
        public String toString() {
            return JSONUtils.toString(this);
        }
    }

    // TODO extract to TradeInformation
    public static class TradeHistory {

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
