package com.triple.pointservice.domain.exception;

public class PointEventAllDeletedException extends BusinessException {
    public PointEventAllDeletedException() {
        super(ErrorCode.POINT_EVENT_ALL_DELETED);
    }
}
