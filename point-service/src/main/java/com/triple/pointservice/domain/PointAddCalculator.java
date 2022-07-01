package com.triple.pointservice.domain;

import com.triple.pointservice.domain.event.PointEvent;
import com.triple.pointservice.domain.event.PointEvents;
import com.triple.pointservice.domain.exception.PointEventAlreadySavedException;

import java.util.Objects;

import static com.triple.pointservice.domain.event.PointEventAction.ADD;
import static com.triple.pointservice.domain.event.PointEventType.*;

public class PointAddCalculator implements PointCalculator {
    private PointEvents events;

    public PointAddCalculator() {
        events = new PointEvents();
    }

    @Override
    public PointEvents calculate(
            Review review, PointPolicy pointPolicy, PointEvents saveEvents, PointEvents savePlaceEvents) {
        calculateContentPoint(review, pointPolicy, saveEvents);
        calculatePlacePoint(review, pointPolicy, savePlaceEvents);
        return events;
    }

    private void calculateContentPoint(Review review, PointPolicy pointPolicy, PointEvents saveEvents) {
        if (saveEvents.invalidAddNewContentEvent()) {
            throw new PointEventAlreadySavedException();
        }

        calculateTextPoint(review, pointPolicy);
        calculatePhotoPoint(review, pointPolicy);
    }

    private void calculateTextPoint(Review review, PointPolicy pointPolicy) {
        String content = review.getContent();
        if (Objects.nonNull(content) && !content.isEmpty()) {
            events.add(PointEvent.create(review, ADD, WRITE_TEXT, pointPolicy.getBasePoint()));
        }
    }

    private void calculatePhotoPoint(Review review, PointPolicy pointPolicy) {
        if (!review.getAttachedPhotoIds().isEmpty()) {
            events.add(PointEvent.create(review, ADD, ATTACHED_PHOTOS, pointPolicy.getBasePoint()));
        }
    }

    private void calculatePlacePoint(Review review, PointPolicy pointPolicy, PointEvents savePlaceEvents) {
        if (savePlaceEvents.isValidAddNewPlaceEvent()) {
            events.add(PointEvent.create(review, ADD, ADDED_FIRST_REVIEW_ON_PLACE, pointPolicy.getBasePoint()));
        }
    }
}
