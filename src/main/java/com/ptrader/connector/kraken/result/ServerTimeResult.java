package com.ptrader.connector.kraken.result;

import com.ptrader.connector.kraken.utils.JSONUtils;

public class ServerTimeResult extends Result<ServerTimeResult.ServerTime> {

    public static class ServerTime {
        public Long unixtime;
        public String rfc1123;

        @Override
        public String toString() {
            return JSONUtils.toString(this);
        }
    }
}
