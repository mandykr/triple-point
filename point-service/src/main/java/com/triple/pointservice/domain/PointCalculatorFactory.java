package com.triple.pointservice.domain;

import com.triple.pointservice.domain.event.ReviewEventAction;
import com.triple.pointservice.domain.exception.InvalidReviewEventActionException;

public class PointCalculatorFactory {
    private static final PointAddEventCalculator POINT_ADD_EVENT_CALCULATOR = new PointAddEventCalculator();
    private static final PointModEventCalculator POINT_MOD_EVENT_CALCULATOR = new PointModEventCalculator();
    private static final PointDeleteEventCalculator POINT_DELETE_EVENT_CALCULATOR = new PointDeleteEventCalculator();

    public static PointEventCalculator of(ReviewEventAction action) {
        switch (action) {
            case ADD:
                return POINT_ADD_EVENT_CALCULATOR;
            case MOD:
                return POINT_MOD_EVENT_CALCULATOR;
            case DELETE:
                return POINT_DELETE_EVENT_CALCULATOR;
            default:
                throw new InvalidReviewEventActionException();
        }
    }
}
