package com.ptrader.connector.kraken.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptrader.connector.kraken.utils.JSONUtils;

import java.math.BigDecimal;
import java.util.List;

public class AddStandardOrderResult extends Result<AddStandardOrderResult.AddStandardOrder> {

    public static class Description
    {
        @JsonProperty("order")
        String orderDescription;

        @JsonProperty("close")
        String closeOrderDescription;
    }
    public static class AddStandardOrder {

        @JsonProperty("desc")
        public Description orderDescription;

        @JsonProperty("txid")
        public List<Long> transcationIds;

        @Override
        public String toString() {
            return JSONUtils.toString(this);
        }
    }
}
