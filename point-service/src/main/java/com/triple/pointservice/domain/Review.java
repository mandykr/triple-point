package com.triple.pointservice.domain;

import com.triple.pointservice.domain.event.ReviewEventAction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Review {
    private ReviewEventAction action;
    private UUID reviewId;
    private String content;
    private List<UUID> attachedPhotoIds;
    private UUID userId;
    private UUID placeId;

    public boolean hasContent() {
        return !content.isEmpty();
    }

    public boolean hasNotContent() {
        return content.isEmpty();
    }

    public boolean hasAttachedPhotos() {
        return !attachedPhotoIds.isEmpty();
    }

    public boolean hasNotAttachedPhotos() {
        return attachedPhotoIds.isEmpty();
    }
}
