package com.triple.pointservice.domain.event;

import com.triple.pointservice.domain.PointPolicy;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.triple.pointservice.domain.ReviewFixture.*;
import static com.triple.pointservice.domain.event.PointEventType.*;

public class PointEventFixture {

    public static PointEvent createEvent(
            PointEventType type, PointEventAction action, PointPolicy pointPolicy, LocalDate date) {
        PointEvent pointEvent = new PointEvent(type, action, REVIEW_ID, USER_ID, PLACE_ID, pointPolicy.getBasePoint());
        ReflectionTestUtils.setField(pointEvent, "createdDate", date.atTime(LocalTime.MIN));
        return pointEvent;
    }

    public static PointEvent createTextEvent(PointEventAction action, PointPolicy pointPolicy, LocalDate date) {
        return createEvent(WRITE_TEXT, action, pointPolicy, date);
    }

    public static PointEvent createPhotoEvent(PointEventAction action, PointPolicy pointPolicy, LocalDate date) {
        return createEvent(ATTACHED_PHOTOS, action, pointPolicy, date);
    }

    public static PointEvent createPlaceEvent(PointEventAction action, PointPolicy pointPolicy, LocalDate date) {
        return createEvent(ADDED_FIRST_REVIEW_ON_PLACE, action, pointPolicy, date);
    }
}
