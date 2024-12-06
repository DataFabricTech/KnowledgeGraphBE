package com.tmax.datafabric.application.exception;

public class InvalidDataObjectIdException extends DataFabricException {
    public static final ErrorCode errorCode = ErrorCode.INVALID_DATAOBJECT_ID;

    public InvalidDataObjectIdException() {
        super(errorCode);
    }

    public InvalidDataObjectIdException(String message) {
        super(message, errorCode);
    }

    public InvalidDataObjectIdException(ErrorCode errorCode, String message) {
        super(message, errorCode);
    }

    public InvalidDataObjectIdException(ErrorCode errorCode) {
        super(errorCode);
    }
}
