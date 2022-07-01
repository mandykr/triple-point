package com.triple.pointservice.domain.exception;

public class PointEventAlreadySavedException extends BusinessException {
    public PointEventAlreadySavedException() {
        super(ErrorCode.POINT_EVENT_ALREADY_SAVED);
    }
}
