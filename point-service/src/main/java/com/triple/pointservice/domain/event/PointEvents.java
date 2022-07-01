package com.triple.pointservice.domain.event;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public boolean invalidAddNewPoint() {
        return !isEmpty() && !allDeleted();
    }

    public boolean isValidAddNewPlacePoint() {
        return isEmpty() || allDeleted();
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
        List<PointEvent> sameTypeEvents = pointEvents.stream()
                .filter(e -> e.getType() == type)
                .collect(Collectors.toList());

        if (sameTypeEvents.isEmpty()) {
            return EmptyPointEvent.getInstance();
        }
        return sameTypeEvents.stream()
                .max(Comparator.comparing(PointEvent::getCreatedDate))
                .orElse(EmptyPointEvent.getInstance());
    }
}
