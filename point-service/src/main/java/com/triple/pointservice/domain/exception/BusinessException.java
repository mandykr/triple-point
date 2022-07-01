package com.triple.pointservice.domain.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
