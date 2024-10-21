package com.tmax.datafabric.application.exception;

public class InvalidAnalysisIdException extends RuntimeException implements DataFabricException {

    private static final int ERROR_CODE = 400;

    private static final String DEFAULT_MESSAGE = "Invalid Analysis Id Requested.";

    public InvalidAnalysisIdException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidAnalysisIdException(String message) {
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
