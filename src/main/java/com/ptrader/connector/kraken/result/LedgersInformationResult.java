package com.ptrader.connector.kraken.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptrader.connector.kraken.result.common.LedgerInformation;
import com.ptrader.connector.kraken.utils.JSONUtils;

import java.util.Map;

public class LedgersInformationResult extends Result<LedgersInformationResult.LedgersInformation> {

    public static class LedgersInformation {

        @JsonProperty("ledger")
        public Map<String, LedgerInformation> ledger;

        public Long count;

        @Override
        public String toString() {
            return JSONUtils.toString(this);
        }
    }
}
