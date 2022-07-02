package com.triple.pointservice.application;

import com.triple.pointservice.application.dto.ReviewEventRequest;
import com.triple.pointservice.domain.event.ReviewEventAction;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.triple.pointservice.domain.ReviewFixture.*;
import static com.triple.pointservice.domain.event.ReviewEventAction.*;

public class ReviewEventRequestFixture {
    public static ReviewEventRequest createReviewRequest(
            ReviewEventAction action, String content, List<UUID> attachedPhotoIds) {
        return new ReviewEventRequest("REVIEW", action, REVIEW_ID, content, attachedPhotoIds, USER_ID, PLACE_ID);
    }

    public static ReviewEventRequest createReviewAddRequest() {
        return createReviewRequest(ADD, CONTENT, ATTACHED_PHOTO_IDS);
    }

    public static ReviewEventRequest createReviewModRequest() {
        return createReviewRequest(MOD, CONTENT, ATTACHED_PHOTO_IDS);
    }

    public static ReviewEventRequest createReviewDeleteContentRequest() {
        return createReviewRequest(MOD, "", ATTACHED_PHOTO_IDS);
    }

    public static ReviewEventRequest createReviewDeletePhotoRequest() {
        return createReviewRequest(MOD, CONTENT, Collections.emptyList());
    }

    public static ReviewEventRequest createReviewDeleteRequest() {
        return createReviewRequest(DELETE, CONTENT, ATTACHED_PHOTO_IDS);
    }
}
