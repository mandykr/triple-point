package com.triple.pointservice.domain.exception;

public class InvalidReviewEventActionException extends BusinessException {
    public InvalidReviewEventActionException() {
        super(ErrorCode.INVALID_REVIEW_EVENT_ACTION);
    }
}
