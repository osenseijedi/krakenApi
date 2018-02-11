package com.ptrader.connector.kraken.result;

import com.ptrader.connector.kraken.utils.JSONUtils;

import java.math.BigDecimal;

public class TradeVolumeResult extends Result<TradeVolumeResult.TradeVolume> {

    public static class TradeVolume {

        public String currency;
        public BigDecimal volume;

        @Override
        public String toString() {
            return JSONUtils.toString(this);
        }
    }
}
