package com.triple.pointservice.domain;

import com.triple.pointservice.domain.event.EmptyPointEvent;
import com.triple.pointservice.domain.event.PointEvent;
import com.triple.pointservice.domain.event.PointEventType;
import com.triple.pointservice.domain.event.PointEvents;
import com.triple.pointservice.domain.exception.PointEventAllDeletedException;
import com.triple.pointservice.domain.exception.PointEventNotFoundException;

import static com.triple.pointservice.domain.event.PointEventAction.ADD;
import static com.triple.pointservice.domain.event.PointEventAction.DELETE;
import static com.triple.pointservice.domain.event.PointEventType.ATTACHED_PHOTOS;
import static com.triple.pointservice.domain.event.PointEventType.WRITE_TEXT;

public class PointModEventCalculator implements PointEventCalculator {
    private PointEvents events;

    public PointModEventCalculator() {
        events = new PointEvents();
    }

    @Override
    public PointEvents calculate(Review review, PointPolicy pointPolicy, PointEventCalculateCondition condition) {
        calculateContentPoint(review, pointPolicy, condition.getSavedEvents());
        return events;
    }

    private void calculateContentPoint(Review review, PointPolicy pointPolicy, PointEvents savedEvents) {
        if (savedEvents.isEmpty()) {
            throw new PointEventNotFoundException();
        }

        PointEvent savedTextEvent = savedEvents.getLastCreatedEvent(PointEventType.WRITE_TEXT);
        PointEvent savedPhotoEvent = savedEvents.getLastCreatedEvent(PointEventType.ATTACHED_PHOTOS);

        if (invalidUpdatePoint(savedTextEvent, savedPhotoEvent)) {
            throw new PointEventAllDeletedException();
        }

        calculateTextPoint(review, pointPolicy, savedTextEvent);
        calculatePhotoPoint(review, pointPolicy, savedPhotoEvent);
    }

    private boolean invalidUpdatePoint(PointEvent savedTextEvent, PointEvent savedPhotoEvent) {
        return savedTextEvent.isDeleteOrEmpty() &&
                savedPhotoEvent.isDeleteOrEmpty();
    }

    private void calculateTextPoint(Review review, PointPolicy pointPolicy, PointEvent savedTextEvent) {
        if (doAddText(review, savedTextEvent)) {
            events.add(PointEvent.create(review, ADD, WRITE_TEXT, pointPolicy.getBasePoint()));
        }
        if (doDeleteText(review, savedTextEvent)) {
            events.add(PointEvent.create(review, DELETE, WRITE_TEXT, pointPolicy.getBasePoint()));
        }
    }

    private void calculatePhotoPoint(Review review, PointPolicy pointPolicy, PointEvent savedPhotoEvent) {
        if (doAttachePhotos(review, savedPhotoEvent)) {
            events.add(PointEvent.create(review, ADD, ATTACHED_PHOTOS, pointPolicy.getBasePoint()));
        }
        if (doDeletePhotos(review, savedPhotoEvent)) {
            events.add(PointEvent.create(review, DELETE, ATTACHED_PHOTOS, pointPolicy.getBasePoint()));
        }
    }

    // ?????? ????????? ????????? ???????????? ??????
    private boolean doAddText(Review review, PointEvent savedTextEvent) {
        return (EmptyPointEvent.isEmpty(savedTextEvent) ||
                savedTextEvent.getAction() == DELETE) &&
                        review.hasContent();
    }

    // ???????????? ????????? ???????????? ??????
    private boolean doDeleteText(Review review, PointEvent savedTextEvent) {
        return EmptyPointEvent.isNotEmpty(savedTextEvent) &&
                savedTextEvent.getAction() == ADD &&
                        review.hasNotContent();
    }

    // ????????? ????????? ?????? ???????????? ??????
    private boolean doAttachePhotos(Review review, PointEvent savedPhotoEvent) {
        return (EmptyPointEvent.isEmpty(savedPhotoEvent) ||
                savedPhotoEvent.getAction() == DELETE) &&
                        review.hasAttachedPhotos();
    }

    // ???????????? ?????? ???????????? ??????
    private boolean doDeletePhotos(Review review, PointEvent savedPhotoEvent) {
        return EmptyPointEvent.isNotEmpty(savedPhotoEvent) &&
                savedPhotoEvent.getAction() == ADD &&
                        review.hasNotAttachedPhotos();
    }
}
