package com.triple.pointservice.domain;

import com.triple.pointservice.domain.event.PointEvent;
import com.triple.pointservice.domain.event.PointEvents;
import com.triple.pointservice.domain.exception.PointEventAlreadySavedException;

import java.util.Objects;

import static com.triple.pointservice.domain.event.PointEventAction.ADD;
import static com.triple.pointservice.domain.event.PointEventType.*;

public class PointAddEventCalculator implements PointEventCalculator {
    private PointEvents events;

    public PointAddEventCalculator() {
        events = new PointEvents();
    }

    @Override
    public PointEvents calculate(Review review, PointPolicy pointPolicy, PointEventCalculateCondition condition) {
        PointEvents savedEvents = condition.getSavedEvents();
        PointEvents savedPlaceEvents = condition.getSavedPlaceEvents();

        PointEvent savedTextEvent = savedEvents.getLastCreatedEvent(WRITE_TEXT);
        PointEvent savedPhotoEvent = savedEvents.getLastCreatedEvent(ATTACHED_PHOTOS);
        PointEvent savedPlaceEvent = savedEvents.getLastCreatedEvent(ADDED_FIRST_REVIEW_ON_PLACE);

        if (!savedEvents.isEmpty() &&
                !isValidAddPoint(savedTextEvent, savedPhotoEvent, savedPlaceEvent)) {
            throw new PointEventAlreadySavedException();
        }

        calculateContentPoint(review, pointPolicy);
        calculatePlacePoint(review, pointPolicy, savedPlaceEvents);
        return events;
    }

    private boolean isValidAddPoint(PointEvent savedTextEvent, PointEvent savedPhotoEvent, PointEvent savedPlaceEvent) {
        return savedTextEvent.isDeleteOrEmpty() &&
                savedPhotoEvent.isDeleteOrEmpty() &&
                savedPlaceEvent.isDeleteOrEmpty();
    }

    private void calculateContentPoint(Review review, PointPolicy pointPolicy) {
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

    private void calculatePlacePoint(Review review, PointPolicy pointPolicy, PointEvents savedPlaceEvents) {
        PointEvent savedTextEvent = savedPlaceEvents.getLastCreatedEvent(WRITE_TEXT);
        PointEvent savedPhotoEvent = savedPlaceEvents.getLastCreatedEvent(ATTACHED_PHOTOS);
        PointEvent savedPlaceEvent = savedPlaceEvents.getLastCreatedEvent(ADDED_FIRST_REVIEW_ON_PLACE);

        if (isValidAddPoint(savedTextEvent, savedPhotoEvent, savedPlaceEvent)) {
            events.add(PointEvent.create(review, ADD, ADDED_FIRST_REVIEW_ON_PLACE, pointPolicy.getBasePoint()));
        }
    }
}
