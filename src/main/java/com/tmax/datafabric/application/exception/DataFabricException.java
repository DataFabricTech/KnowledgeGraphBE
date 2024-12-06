package com.tmax.datafabric.application.exception;

public class DataFabricException extends RuntimeException {
    private ErrorCode errorCode;

    public DataFabricException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DataFabricException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }


    public ErrorCode getErrorCode() {
        return this.errorCode;
    };

    public String getMessage() {
        return this.errorCode.getMessage();
    };
}
