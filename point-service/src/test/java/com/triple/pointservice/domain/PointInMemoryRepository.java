package com.triple.pointservice.domain;

import com.triple.pointservice.domain.repository.PointRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PointInMemoryRepository implements PointRepository {
    private final Map<UUID, Point> points = new HashMap<>();

    @Override
    public Optional<Point> findByUserId(UUID userId) {
        return points.values()
                .stream()
                .filter(point -> userId.equals(point.getUserId()))
                .findFirst();
    }

    @Override
    public Point save(Point point) {
        points.put(point.getId(), point);
        return point;
    }
}
