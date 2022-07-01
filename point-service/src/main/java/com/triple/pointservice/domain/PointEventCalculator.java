package com.triple.pointservice.domain;

import com.triple.pointservice.domain.event.PointEvents;

public interface PointEventCalculator {
    PointEvents calculate(Review review, PointPolicy pointPolicy, PointEvents savedEvents, PointEvents savedPlaceEvents);
}
