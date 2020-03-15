package com.ptrader.connector.kraken.input.exceptions;

public class InvalidStateException extends Exception {
    public InvalidStateException() {}

    public InvalidStateException(String message)
    {
        super(message);
    }
}
