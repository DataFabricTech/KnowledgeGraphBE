package com.tmax.datafabric.application.exception;

public class InvalidCategoryIdException extends DataFabricException {
    public static final ErrorCode errorCode = ErrorCode.INVALID_CATEGORY_ID;

    public InvalidCategoryIdException() {
        super(errorCode);
    }

    public InvalidCategoryIdException(String message) {
        super(message, errorCode);
    }

    public InvalidCategoryIdException(ErrorCode errorCode, String message) {
        super(message, errorCode);
    }

    public InvalidCategoryIdException(ErrorCode errorCode) {
        super(errorCode);
    }
}