package com.ptrader.connector.kraken.result;

import com.ptrader.connector.kraken.utils.JSONUtils;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {

    private List<String> error = new ArrayList<>();
    private T result;

    public List<String> getError() {
        return error;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return JSONUtils.toString(this);
    }
}
