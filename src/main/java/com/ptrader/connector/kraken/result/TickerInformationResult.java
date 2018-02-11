package com.ptrader.connector.kraken.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ptrader.connector.kraken.utils.JSONUtils;

import java.math.BigDecimal;
import java.util.Map;

public class TickerInformationResult extends Result<Map<String, TickerInformationResult.TickerInformation>> {

    public static class TickerInformation {

        @JsonFormat(shape = JsonFormat.Shape.ARRAY)
        @JsonPropertyOrder({"price", "wholeLotVolume", "lotVolume"})
        public static class PriceWholeLotVolume {
            public BigDecimal price;
            public Integer wholeLotVolume;
            public BigDecimal lotVolume;

            @Override
            public String toString() {
                return JSONUtils.toString(this);
            }
        }

        @JsonFormat(shape = JsonFormat.Shape.ARRAY)
        @JsonPropertyOrder({"price", "lotVolume"})
        public static class PriceLotVolume {
            public BigDecimal price;
            public BigDecimal lotVolume;

            @Override
            public String toString() {
                return JSONUtils.toString(this);
            }
        }

        @JsonFormat(shape = JsonFormat.Shape.ARRAY)
        @JsonPropertyOrder({"today", "last24hours"})
        public static class TodayLast24h {
            public BigDecimal today;
            public BigDecimal last24hours;

            @Override
            public String toString() {
                return JSONUtils.toString(this);
            }
        }

        @JsonFormat(shape = JsonFormat.Shape.ARRAY)
        @JsonPropertyOrder({"today", "last24hours"})
        public static class NumberOfTrade {
            public Integer today;
            public Integer last24hours;

            @Override
            public String toString() {
                return JSONUtils.toString(this);
            }
        }

        @JsonProperty("a")
        public PriceWholeLotVolume ask;

        @JsonProperty("b")
        public PriceWholeLotVolume bid;

        @JsonProperty("c")
        public PriceLotVolume lastTradeClosed;

        @JsonProperty("v")
        public TodayLast24h volume;

        @JsonProperty("p")
        public TodayLast24h volumeWeightAverage;

        @JsonProperty("t")
        public NumberOfTrade numberOfTrades;

        @JsonProperty("l")
        public TodayLast24h low;

        @JsonProperty("h")
        public TodayLast24h high;

        @JsonProperty("o")
        public BigDecimal todayOpenPrice;

        @Override
        public String toString() {
            return JSONUtils.toString(this);
        }
    }
}
