package com.ptrader.connector;

import com.ptrader.connector.kraken.utils.JSONUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiJsonResponse {

    private String rawResponse;

    @Override
    public String toString() {
        return JSONUtils.toString(this);
    }
}
