package com.ptrader.connector.kraken.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ptrader.connector.kraken.result.common.OrderDirection;
import com.ptrader.connector.kraken.result.common.OrderType;
import com.ptrader.connector.kraken.utils.JSONUtils;

import java.math.BigDecimal;
import java.util.Map;

public class OrdersInformationResult extends Result<Map<String, OrdersInformationResult.OrderInfo>> {

    public static class OrderInfo {

        public enum Status {
            PENDING("pending"),
            OPEN("open"),
            CLOSED("closed"),
            CANCELED("canceled"),
            EXPIRED("expired");

            private String value;

            Status(String value) {
                this.value = value;
            }

            @JsonValue
            public String getValue() {
                return value;
            }
        }

        public static class Description {

            @JsonProperty("pair")
            public String assetPair;

            @JsonProperty("type")
            public OrderDirection orderDirection;

            @JsonProperty("ordertype")
            public OrderType orderType;

            public BigDecimal price;

            @JsonProperty("price2")
            public BigDecimal secondaryPrice;

            public String leverage;

            public String order;

            @Override
            public String toString() {
                return JSONUtils.toString(this);
            }
        }

        @JsonProperty("refid")
        public String referralOrderTransactionId;

        @JsonProperty("userref")
        public String userReferenceId;

        public Status status;

        @JsonProperty("opentm")
        public Long openTimestamp;

        @JsonProperty("starttm")
        public Long orderStartTimestamp;

        @JsonProperty("expiretm")
        public Long orderEndTimestamp;

        @JsonProperty("descr")
        public Description description;

        @JsonProperty("vol")
        public BigDecimal volumeOrder;

        @JsonProperty("vol_exec")
        public BigDecimal volumeExecuted;

        public BigDecimal cost;

        public BigDecimal fee;

        @JsonProperty("price")
        public BigDecimal averagePrice;

        @JsonProperty("stopprice")
        public BigDecimal stopPrice;

        @JsonProperty("misc")
        public String miscellaneous;

        @JsonProperty("oflags")
        public String orderFlags;

        public String reason;

        @JsonProperty("closetm")
        public Long orderClosedTimestamp;

        @Override
        public String toString() {
            return JSONUtils.toString(this);
        }
    }
}
