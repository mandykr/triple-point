package com.triple.pointservice.application;

import com.triple.pointservice.application.dto.PointResponse;
import com.triple.pointservice.domain.Point;
import com.triple.pointservice.domain.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class PointService {
    private final PointRepository pointRepository;

    public PointResponse findPoint(UUID userId) {
        Optional<Point> point = pointRepository.findByUserId(userId);
        if (point.isEmpty()) {
            return PointResponse.empty();
        }
        return PointResponse.of(point.get());
    }
}
