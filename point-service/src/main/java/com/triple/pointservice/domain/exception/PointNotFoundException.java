package com.triple.pointservice.domain.exception;

public class PointNotFoundException extends BusinessException {
    public PointNotFoundException() {
        super(ErrorCode.POINT_NOT_FOUND);
    }
}
