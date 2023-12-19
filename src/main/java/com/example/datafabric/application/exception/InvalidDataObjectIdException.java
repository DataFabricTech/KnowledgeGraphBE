package com.example.datafabric.application.exception;

public class InvalidDataObjectIdException extends RuntimeException implements DataFabricException {

    private static final int ERROR_CODE = 400;

    private static final String DEFAULT_MESSAGE = "DataObject Id Cannot Found.";

    public InvalidDataObjectIdException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidDataObjectIdException(String message) {
        super(message);
    }

    @Override
    public int getErrorCode() {
        return ERROR_CODE;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
