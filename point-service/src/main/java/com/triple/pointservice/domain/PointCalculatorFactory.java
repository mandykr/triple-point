package com.triple.pointservice.domain;

import com.triple.pointservice.domain.event.ReviewEventAction;

public class PointCalculatorFactory {
    private static final PointAddCalculator POINT_ADD_CALCULATOR = new PointAddCalculator();

    public static PointCalculator of(ReviewEventAction action) {
        switch (action) {
            case ADD:
                return POINT_ADD_CALCULATOR;
            default:
                throw new IllegalArgumentException();
        }
    }
}
