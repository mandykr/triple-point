package com.triple.pointservice.domain;

import com.triple.pointservice.domain.event.ReviewEventAction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ReviewFixture {
    public static final UUID REVIEW_ID;
    public static final UUID USER_ID;
    public static final UUID PLACE_ID;
    public static final String CONTENT;
    public static final List<UUID> ATTACHED_PHOTO_IDS;

    static {
        REVIEW_ID = UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667772");
        USER_ID = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745");
        PLACE_ID = UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f");
        CONTENT = "좋아요!";
        ATTACHED_PHOTO_IDS = Arrays.asList(
                UUID.fromString("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"),
                UUID.fromString("afb0cef2-851d-4a50-bb07-9cc15cbdc332")
        );
    }

    public static Review createReview(ReviewEventAction action, String content, List<UUID> attachedPhotoIds) {
        return new Review(action, REVIEW_ID, content, attachedPhotoIds, USER_ID, PLACE_ID);
    }

    public static Review createTextReview(ReviewEventAction action) {
        return createReview(action, CONTENT, Collections.emptyList());
    }

    public static Review createPhotoReview(ReviewEventAction action) {
        return createReview(action, "", ATTACHED_PHOTO_IDS);
    }
}
