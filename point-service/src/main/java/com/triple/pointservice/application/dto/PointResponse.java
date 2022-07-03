package com.triple.pointservice.application.dto;

import com.triple.pointservice.domain.Point;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class PointResponse {
    private UUID id;
    private UUID userId;
    private int point;

    private PointResponse() {
    }

    public static PointResponse of(Point point) {
        return new PointResponse(point.getId(), point.getUserId(), point.getPoint());
    }

    public static PointResponse empty(UUID userId) {
        return new PointResponse(null, userId, 0);
    }
}
