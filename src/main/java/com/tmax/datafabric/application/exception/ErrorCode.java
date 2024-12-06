package com.tmax.datafabric.application.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_ANALYSIS_ID(400, "Analysis-001", "Invalid Analysis Id Requested."),
    INVALID_CATEGORY_ID(400, "Category-001", "Category Id Cannot Found."),
    INVALID_DATAOBJECT_ID(400, "Dataobject-001", "DataObject Id Cannot Found.");

    private final int status;
    private final String code;
    private final String message;


}
