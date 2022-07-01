package com.triple.pointservice.domain.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.triple.pointservice.domain.ReviewFixture.*;
import static com.triple.pointservice.domain.event.PointEventAction.ADD;
import static com.triple.pointservice.domain.event.PointEventAction.DELETE;
import static com.triple.pointservice.domain.event.PointEventType.ADDED_FIRST_REVIEW_ON_PLACE;
import static com.triple.pointservice.domain.event.PointEventType.WRITE_TEXT;
import static org.assertj.core.api.Assertions.assertThat;

class PointEventsTest {
    @DisplayName("이벤트 리스트가 비어있으면 참을 반환한다")
    @Test
    void isEmpty() {
        // given
        PointEvents pointEvents = new PointEvents();

        // when, then
        assertThat(pointEvents.isEmpty()).isTrue();
    }

    @DisplayName("새로운 이벤트를 추가한다")
    @Test
    void add() {
        // given
        PointEvent pointEvent = new PointEvent(WRITE_TEXT, ADD, REVIEW_ID, USER_ID, PLACE_ID, 1);
        PointEvents pointEvents = new PointEvents();

        // when
        pointEvents.add(pointEvent);

        // then
        int size = pointEvents.getPointEvents().size();
        assertThat(size).isEqualTo(1);
    }

    @DisplayName("모든 이벤트가 삭제된 상태이면 참을 반환한다")
    @Test
    void allDeleted() {
        // given
        PointEvents pointEvents = new PointEvents(
                Arrays.asList(
                        new PointEvent(ADDED_FIRST_REVIEW_ON_PLACE, DELETE, REVIEW_ID, USER_ID, PLACE_ID, 1),
                        new PointEvent(ADDED_FIRST_REVIEW_ON_PLACE, DELETE, REVIEW_ID, USER_ID, PLACE_ID, 1),
                        new PointEvent(ADDED_FIRST_REVIEW_ON_PLACE, DELETE, REVIEW_ID, USER_ID, PLACE_ID, 1),
                        new PointEvent(ADDED_FIRST_REVIEW_ON_PLACE, DELETE, REVIEW_ID, USER_ID, PLACE_ID, 1),
                        new PointEvent(ADDED_FIRST_REVIEW_ON_PLACE, DELETE, REVIEW_ID, USER_ID, PLACE_ID, 1)
                )
        );

        // when, then
        assertThat(pointEvents.allDeleted()).isTrue();
    }

    @DisplayName("모든 이벤트가 삭제된 상태가 아니면 거짓을 반환한다")
    @Test
    void allNotDeleted() {
        // given
        PointEvents pointEvents = new PointEvents(
                Arrays.asList(
                        new PointEvent(ADDED_FIRST_REVIEW_ON_PLACE, DELETE, REVIEW_ID, USER_ID, PLACE_ID, 1),
                        new PointEvent(ADDED_FIRST_REVIEW_ON_PLACE, ADD, REVIEW_ID, USER_ID, PLACE_ID, 1),
                        new PointEvent(ADDED_FIRST_REVIEW_ON_PLACE, ADD, REVIEW_ID, USER_ID, PLACE_ID, 1),
                        new PointEvent(ADDED_FIRST_REVIEW_ON_PLACE, DELETE, REVIEW_ID, USER_ID, PLACE_ID, 1),
                        new PointEvent(ADDED_FIRST_REVIEW_ON_PLACE, ADD, REVIEW_ID, USER_ID, PLACE_ID, 1)
                )
        );

        // when, then
        assertThat(pointEvents.allDeleted()).isFalse();
    }
}
