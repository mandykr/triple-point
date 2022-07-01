package com.triple.pointservice.domain;

import org.springframework.stereotype.Component;

@Component
public class DefaultPointPolicy implements PointPolicy {
    private static final int basePoint = 1;

    @Override
    public int getBasePoint() {
        return basePoint;
    }
}
