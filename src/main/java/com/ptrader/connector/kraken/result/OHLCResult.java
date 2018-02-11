package com.ptrader.connector.kraken.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ptrader.connector.kraken.utils.JSONUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OHLCResult extends ResultWithLastId<Map<String, List<OHLCResult.OHLC>>> {

    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    @JsonPropertyOrder({"time", "open", "high", "low", "close", "vwap", "volume", "count"})
    public static class OHLC {
        public Integer time;
        public BigDecimal open;
        public BigDecimal high;
        public BigDecimal low;
        public BigDecimal close;
        public BigDecimal vwap;
        public BigDecimal volume;
        public Integer count;

        @Override
        public String toString() {
            return JSONUtils.toString(this);
        }
    }

    @Override
    public String toString() {
        return JSONUtils.toString(this);
    }
}
