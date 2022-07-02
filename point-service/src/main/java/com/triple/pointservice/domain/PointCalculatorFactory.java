package com.triple.pointservice.domain;

import com.triple.pointservice.domain.event.ReviewEventAction;
import com.triple.pointservice.domain.exception.InvalidReviewEventActionException;

public class PointCalculatorFactory {
    public static PointEventCalculator of(ReviewEventAction action) {
        switch (action) {
            case ADD:
                return new PointAddEventCalculator();
            case MOD:
                return new PointModEventCalculator();
            case DELETE:
                return new PointDeleteEventCalculator();
            default:
                throw new InvalidReviewEventActionException();
        }
    }
}
