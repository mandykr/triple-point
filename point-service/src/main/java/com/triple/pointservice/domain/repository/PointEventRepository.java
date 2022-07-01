package com.triple.pointservice.domain.repository;

import com.triple.pointservice.domain.event.PointEvent;

import java.util.List;
import java.util.UUID;

public interface PointEventRepository {
    PointEvent save(PointEvent pointEvent);

    List<PointEvent> findByReviewId(UUID reviewId);

    List<PointEvent> findByPlaceId(UUID placeId);
}
