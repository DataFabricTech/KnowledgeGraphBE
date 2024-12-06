package com.tmax.datafabric.application.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {InvalidAnalysisIdException.class, InvalidCategoryIdException.class,
    InvalidDataObjectIdException.class})
    protected ResponseEntity<Object> handleError(DataFabricException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(ex.getErrorCode());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(),
                HttpStatus.valueOf(ex.getErrorCode().getStatus()), request);
    }
}
