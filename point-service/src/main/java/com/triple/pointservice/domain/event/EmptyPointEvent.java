package com.triple.pointservice.domain.event;

import java.util.UUID;

public class EmptyPointEvent extends PointEvent {
    public static final int EMPTY_POINT = -1;

    private EmptyPointEvent(PointEventType type, PointEventAction action, UUID reviewId, UUID userId, UUID placeId, int point) {
        super(type, action, reviewId, userId, placeId, point);
    }

    private static class EmptyPointEventHolder {
        private static final EmptyPointEvent INSTANCE =
                new EmptyPointEvent(null, null, null, null, null, EMPTY_POINT);
    }

    public static EmptyPointEvent getInstance() {
        return EmptyPointEventHolder.INSTANCE;
    }

    public static boolean isEmpty(PointEvent event) {
        return getInstance().equals(event);
    }

    public static boolean isNotEmpty(PointEvent event) {
        return !getInstance().equals(event);
    }
}
