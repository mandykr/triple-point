package com.triple.pointservice.domain.exception;

public enum ErrorCode {
    POINT_EVENT_ALREADY_SAVED(400, "리뷰에 대한 이벤트가 이미 저장되어 있습니다."),
    INVALID_REVIEW_EVENT_ACTION(400, "유효하지 않은 리뷰 이벤트 액션입니다."),
    POINT_EVENT_NOT_FOUND(400, "리뷰에 대한 이벤트가 존재하지 않습니다."),
    POINT_EVENT_ALL_DELETED(400, "리뷰에 대한 이벤트가 모두 삭제되었습니다."),
    POINT_NOT_FOUND(400, "사용자 포인트가 존재하지 않습니다.")
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
