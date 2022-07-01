package com.triple.pointservice.domain;

import com.triple.pointservice.domain.event.EmptyPointEvent;
import com.triple.pointservice.domain.event.PointEvent;
import com.triple.pointservice.domain.event.PointEventType;
import com.triple.pointservice.domain.event.PointEvents;
import com.triple.pointservice.domain.exception.PointEventAllDeletedException;
import com.triple.pointservice.domain.exception.PointEventNotFoundException;

import static com.triple.pointservice.domain.event.PointEventAction.ADD;
import static com.triple.pointservice.domain.event.PointEventAction.DELETE;
import static com.triple.pointservice.domain.event.PointEventType.*;

public class PointDeleteEventCalculator implements PointEventCalculator {
    private PointEvents events;

    public PointDeleteEventCalculator() {
        events = new PointEvents();
    }

    @Override
    public PointEvents calculate(Review review, PointPolicy pointPolicy, PointEventCalculateCondition condition) {
        PointEvents savedEvents = condition.getSavedEvents();

        if (savedEvents.isEmpty()) {
            throw new PointEventNotFoundException();
        }

        PointEvent savedTextEvent = savedEvents.getLastCreatedEvent(PointEventType.WRITE_TEXT);
        PointEvent savedPhotoEvent = savedEvents.getLastCreatedEvent(ATTACHED_PHOTOS);
        PointEvent savedPlaceEvent = savedEvents.getLastCreatedEvent(ADDED_FIRST_REVIEW_ON_PLACE);

        if (invalidDeletePoint(savedTextEvent, savedPhotoEvent, savedPlaceEvent)) {
            throw new PointEventAllDeletedException();
        }

        calculateTextPoint(review, pointPolicy, savedTextEvent);
        calculatePhotoPoint(review, pointPolicy, savedPhotoEvent);
        calculatePlacePoint(review, pointPolicy, savedPlaceEvent);

        return events;
    }

    private boolean invalidDeletePoint(
            PointEvent savedTextEvent, PointEvent savedPhotoEvent, PointEvent savedPlaceEvent) {
        return savedTextEvent.isDeleteEvent() &&
                savedPhotoEvent.isDeleteEvent() &&
                savedPlaceEvent.isDeleteEvent();
    }

    private void calculateTextPoint(Review review, PointPolicy pointPolicy, PointEvent savedTextEvent) {
        if (hasContentPoint(savedTextEvent)) {
            events.add(PointEvent.create(review, DELETE, WRITE_TEXT, pointPolicy.getBasePoint()));
        }
    }

    private void calculatePhotoPoint(Review review, PointPolicy pointPolicy, PointEvent savedPhotoEvent) {
        if (hasPhotoPoint(savedPhotoEvent)) {
            events.add(PointEvent.create(review, DELETE, ATTACHED_PHOTOS, pointPolicy.getBasePoint()));
        }
    }

    private void calculatePlacePoint(Review review, PointPolicy pointPolicy, PointEvent savedPlaceEvent) {
        if (hasPlacePoint(savedPlaceEvent)) {
            events.add(PointEvent.create(review, DELETE, ADDED_FIRST_REVIEW_ON_PLACE, pointPolicy.getBasePoint()));
        }
    }

    private boolean hasContentPoint(PointEvent savedTextEvent) {
        return EmptyPointEvent.isNotEmpty(savedTextEvent) &&
                savedTextEvent.getAction() == ADD;
    }

    private boolean hasPhotoPoint(PointEvent savedPhotoEvent) {
        return EmptyPointEvent.isNotEmpty(savedPhotoEvent) &&
                savedPhotoEvent.getAction() == ADD;
    }

    private boolean hasPlacePoint(PointEvent savedPlaceEvent) {
        return EmptyPointEvent.isNotEmpty(savedPlaceEvent) &&
                savedPlaceEvent.getAction() == ADD;
    }
}
