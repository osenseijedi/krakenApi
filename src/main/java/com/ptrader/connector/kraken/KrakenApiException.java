package com.ptrader.connector.kraken;

import java.util.ArrayList;
import java.util.List;

public class KrakenApiException extends Exception {

    private List<String> errors = new ArrayList<>();

    public KrakenApiException(String error) {
        this.addError(error);
    }

    KrakenApiException(List<String> errors) {
        this.errors = errors;
    }

    public void addError(String error) {
        this.errors.add(error);
    }

    public String getMessage() {
        return errors.toString();
    }
}
