package com.ptrader.connector.kraken.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ptrader.connector.kraken.utils.JSONUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class RecentTradeResult extends ResultWithLastId<Map<String, List<RecentTradeResult.RecentTrade>>> {

    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    @JsonPropertyOrder({"price", "volume", "time", "buySell", "marketLimit", "miscellaneous"})
    public static class RecentTrade {
        public BigDecimal price;
        public BigDecimal volume;
        public BigDecimal time;

        public Object buySell;
        public String marketLimit;
        public String miscellaneous;

        @Override
        public String toString() {
            return JSONUtils.toString(this);
        }
    }
}
