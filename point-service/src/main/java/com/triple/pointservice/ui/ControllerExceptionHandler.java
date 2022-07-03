package com.triple.pointservice.ui;

import com.triple.pointservice.domain.exception.BusinessException;
import com.triple.pointservice.domain.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Void> handleBusinessException(BusinessException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return new ResponseEntity<>(HttpStatus.valueOf(errorCode.getStatus()));
    }
}
