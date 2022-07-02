package com.triple.pointservice.domain;

import com.triple.pointservice.domain.event.PointEvent;
import com.triple.pointservice.domain.repository.PointEventRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class PointEventInMemoryRepository implements PointEventRepository {
    private final Map<Long, PointEvent> pointEvents = new HashMap<>();
    private long id = 1;

    @Override
    public PointEvent save(PointEvent pointEvent) {
        pointEvents.put(id++, pointEvent);
        return pointEvent;
    }

    @Override
    public List<PointEvent> findByReviewId(UUID reviewId) {
        return pointEvents.values()
                .stream()
                .filter(event -> reviewId.equals(event.getReviewId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PointEvent> findByPlaceId(UUID placeId) {
        return pointEvents.values()
                .stream()
                .filter(event -> placeId.equals(event.getPlaceId()))
                .collect(Collectors.toList());
    }
}
