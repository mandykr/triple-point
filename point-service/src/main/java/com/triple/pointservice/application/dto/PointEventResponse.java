package com.triple.pointservice.application.dto;

import com.triple.pointservice.domain.event.PointEvent;
import com.triple.pointservice.domain.event.PointEventAction;
import com.triple.pointservice.domain.event.PointEventType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PointEventResponse {
    private Long id;
    private PointEventType type;
    private PointEventAction action;
    private UUID reviewId;
    private UUID userId;
    private UUID placeId;
    private int point;

    public static PointEventResponse of(PointEvent pointEvent) {
        return new PointEventResponse(
                pointEvent.getId(),
                pointEvent.getType(),
                pointEvent.getAction(),
                pointEvent.getReviewId(),
                pointEvent.getUserId(),
                pointEvent.getPlaceId(),
                pointEvent.getPoint());
    }
}
