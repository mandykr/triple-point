package com.triple.pointservice.domain.exception;

public enum ErrorCode {
    POINT_EVENT_ALREADY_SAVED(400, "리뷰 등록에 대한 이벤트가 이미 저장되어 있습니다."),
    ;

    private final String message;
    private final int status;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
