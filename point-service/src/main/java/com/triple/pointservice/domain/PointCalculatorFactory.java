package com.triple.pointservice.domain;

import com.triple.pointservice.domain.event.ReviewEventAction;
import com.triple.pointservice.domain.exception.InvalidReviewEventActionException;

public class PointCalculatorFactory {
    private static final PointAddCalculator POINT_ADD_CALCULATOR = new PointAddCalculator();
    private static final PointModCalculator POINT_MOD_CALCULATOR = new PointModCalculator();

    public static PointCalculator of(ReviewEventAction action) {
        switch (action) {
            case ADD:
                return POINT_ADD_CALCULATOR;
            case MOD:
                return POINT_MOD_CALCULATOR;
            default:
                throw new InvalidReviewEventActionException();
        }
    }
}
