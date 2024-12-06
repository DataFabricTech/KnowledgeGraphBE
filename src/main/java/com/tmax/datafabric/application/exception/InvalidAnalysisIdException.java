package com.tmax.datafabric.application.exception;

public class InvalidAnalysisIdException extends DataFabricException {
    public static final ErrorCode errorCode = ErrorCode.INVALID_ANALYSIS_ID;

    public InvalidAnalysisIdException() {
        super(errorCode);
    }

    public InvalidAnalysisIdException(String message) {
        super(message, errorCode);
    }

    public InvalidAnalysisIdException(ErrorCode errorCode, String message) {
        super(message, errorCode);
    }

    public InvalidAnalysisIdException(ErrorCode errorCode) {
        super(errorCode);
    }
}
