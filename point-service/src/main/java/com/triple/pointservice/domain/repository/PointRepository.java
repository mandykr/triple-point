package com.triple.pointservice.domain.repository;

import com.triple.pointservice.domain.Point;

import java.util.Optional;
import java.util.UUID;

public interface PointRepository {
    Optional<Point> findByUserId(UUID userId);

    Point save(Point point);
}
