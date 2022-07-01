package com.triple.pointservice.domain.exception;

public class PointEventNotFoundException extends BusinessException {
    public PointEventNotFoundException() {
        super(ErrorCode.POINT_EVENT_NOT_FOUND);
    }
}
