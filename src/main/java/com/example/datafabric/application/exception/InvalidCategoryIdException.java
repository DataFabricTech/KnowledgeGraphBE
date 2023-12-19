package com.example.datafabric.application.exception;

public class InvalidCategoryIdException extends RuntimeException implements DataFabricException {

    private static final int ERROR_CODE = 400;

    private static final String DEFAULT_MESSAGE = "Category Id Cannot Found.";

    public InvalidCategoryIdException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidCategoryIdException(String message) {
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