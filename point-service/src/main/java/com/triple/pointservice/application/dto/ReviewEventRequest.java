package com.triple.pointservice.application.dto;

import com.triple.pointservice.domain.Review;
import com.triple.pointservice.domain.event.ReviewEventAction;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ReviewEventRequest {
    private String type;
    private ReviewEventAction action;
    private UUID reviewId;
    private String content;
    private List<UUID> attachedPhotoIds;
    private UUID userId;
    private UUID placeId;

    public Review toReview() {
        return new Review(action, reviewId, content, attachedPhotoIds, userId, placeId);
    }
}
