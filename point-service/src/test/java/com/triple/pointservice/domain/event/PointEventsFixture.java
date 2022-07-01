package com.triple.pointservice.domain.event;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PointEventsFixture {
    public static PointEvents createPointEvents(PointEvent... events) {
        return new PointEvents(Arrays.asList(events));
    }

    public static int getSize(PointEvents events) {
        return events.getPointEvents().size();
    }

    public static List<PointEventType> getTypes(PointEvents events) {
        return events.getPointEvents().stream()
                .map(PointEvent::getType)
                .collect(Collectors.toList());
    }

    public static List<PointEventAction> getActions(PointEvents events) {
        return events.getPointEvents().stream()
                .map(PointEvent::getAction)
                .collect(Collectors.toList());
    }
}
