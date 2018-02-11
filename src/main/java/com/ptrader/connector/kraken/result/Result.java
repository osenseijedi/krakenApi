package com.ptrader.connector.kraken.result;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("error", error)
                .append("result", result)
                .toString();
    }
}
