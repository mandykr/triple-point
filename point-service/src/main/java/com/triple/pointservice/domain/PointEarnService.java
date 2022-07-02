package com.triple.pointservice.domain;

import com.triple.pointservice.domain.exception.PointNotFoundException;
import com.triple.pointservice.domain.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class PointEarnService {
    private final PointRepository pointRepository;
    private final PointPolicy pointPolicy;

    public Point addPoint(UUID userId) {
        Point point = pointRepository.findByUserId(userId)
                .orElse(Point.create(UUID.randomUUID(), userId));
        point.add(pointPolicy.getBasePoint());
        return pointRepository.save(point);
    }

    public Point subPoint(UUID userId) {
        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(PointNotFoundException::new);
        point.sub(pointPolicy.getBasePoint());
        return pointRepository.save(point);
    }
}
