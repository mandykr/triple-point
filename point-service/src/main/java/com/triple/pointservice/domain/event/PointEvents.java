package com.triple.pointservice.domain.event;

import java.util.ArrayList;
import java.util.List;

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

    public boolean invalidAddNewContentEvent() {
        return !isEmpty() && !allDeleted();
    }

    public boolean isValidAddNewPlaceEvent() {
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
}
