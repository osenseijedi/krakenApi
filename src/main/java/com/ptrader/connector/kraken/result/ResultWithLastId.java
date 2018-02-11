package com.ptrader.connector.kraken.result;

import com.ptrader.connector.kraken.utils.JSONUtils;

public class ResultWithLastId<T> extends Result<T> {
    private Long lastId = 0L;

    public Long getLastId() {
        return lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }

    @Override
    public String toString() {
        return JSONUtils.toString(this);
    }
}
