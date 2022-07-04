package com.triple.pointservice.domain.event;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static com.triple.pointservice.domain.event.PointEventAction.DELETE;

public class PointEvents {
    private List<PointEvent> pointEvents;

    public PointEvents(List<PointEvent> pointEvents) {
        this.pointEvents = pointEvents;
    }

    public PointEvents() {
        pointEvents = new ArrayList<>();
    }

    public List<PointEvent> getPointEvents() {
        return pointEvents;
    }

    public boolean isEmpty() {
        return pointEvents.isEmpty();
    }

    public void add(PointEvent pointEvent) {
        pointEvents.add(pointEvent);
    }

    public boolean allDeleted() {
        return pointEvents.stream()
                .allMatch(e -> e.getAction() == DELETE);
    }

    public PointEvent getLastCreatedEvent(PointEventType type) {
        return pointEvents.stream()
                .filter(e -> e.getType() == type)
                .max(Comparator.comparing(PointEvent::getCreatedDate))
                .orElse(EmptyPointEvent.getInstance());
    }

    public PointEvent getLastCreatedEvent(PointEventType type, UUID reviewId) {
        return pointEvents.stream()
                .filter(e -> e.getType() == type &&
                        e.getReviewId().equals(reviewId))
                .max(Comparator.comparing(PointEvent::getCreatedDate))
                .orElse(EmptyPointEvent.getInstance());
    }
}
