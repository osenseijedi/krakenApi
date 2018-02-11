package com.ptrader.connector.kraken.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptrader.connector.kraken.utils.JSONUtils;

import java.util.Map;

public class AssetsInformationResult extends Result<Map<String, AssetsInformationResult.AssetInformation>> {

    public static class AssetInformation {

        @JsonProperty("altname")
        public String alternateName;

        @JsonProperty("aclass")
        public String assetClass;

        public Byte decimals;

        @JsonProperty("display_decimals")
        public Byte displayDecimals;

        @Override
        public String toString() {
            return JSONUtils.toString(this);
        }
    }
}
