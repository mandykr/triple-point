package com.triple.pointservice.domain;

import com.triple.pointservice.domain.event.PointEvents;

public interface PointCalculator {
    PointEvents calculate(Review review, PointPolicy pointPolicy, PointEvents saveEvents, PointEvents savePlaceEvents);
}
