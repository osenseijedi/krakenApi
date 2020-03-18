package com.ptrader.connector.kraken.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptrader.connector.kraken.common.OrderDirection;
import com.ptrader.connector.kraken.common.OrderType;
import com.ptrader.connector.kraken.utils.JSONUtils;

import java.math.BigDecimal;
import java.util.Map;

public class OpenPositionsResult extends Result<Map<String, OpenPositionsResult.OpenPosition>> {

    public static class OpenPosition {

        @JsonProperty("ordertxid")
        public String orderTransactionId;

        @JsonProperty("posstatus")
        public String positionStatus;

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

        @JsonProperty("vol_closed")
        public BigDecimal volumeCosed;

        public BigDecimal margin;

        public String terms;

        @JsonProperty("rollovertm")
        public Long rolloverTimestamp;

        @JsonProperty("misc")
        public String miscellaneous;

        @JsonProperty("oflags")
        public String orderFlags;

        @Override
        public String toString() {
            return JSONUtils.toString(this);
        }
    }
}
