package com.triple.pointservice.domain;

import com.triple.pointservice.domain.event.PointEvents;
import lombok.Getter;

@Getter
public class PointEventCalculateCondition {
    private PointEvents savedEvents;
    private PointEvents savedPlaceEvents;

    public PointEventCalculateCondition(PointEvents savedEvents, PointEvents savedPlaceEvents) {
        this.savedEvents = savedEvents;
        this.savedPlaceEvents = savedPlaceEvents;
    }

    public static PointEventCalculateCondition savedEventsCondition(PointEvents savedEvents) {
        return new PointEventCalculateCondition(savedEvents, new PointEvents());
    }

    public static PointEventCalculateCondition savedPlaceEventsCondition(PointEvents savedPlaceEvents) {
        return new PointEventCalculateCondition(new PointEvents(), savedPlaceEvents);
    }
}
