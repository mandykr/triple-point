package com.triple.pointservice.domain;

import com.triple.pointservice.domain.event.PointEvents;
import com.triple.pointservice.domain.event.ReviewEventAction;
import com.triple.pointservice.domain.exception.PointEventAllDeletedException;
import com.triple.pointservice.domain.exception.PointEventNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.triple.pointservice.domain.PointEventCalculateCondition.savedEventsCondition;
import static com.triple.pointservice.domain.ReviewFixture.*;
import static com.triple.pointservice.domain.event.PointEventAction.ADD;
import static com.triple.pointservice.domain.event.PointEventAction.DELETE;
import static com.triple.pointservice.domain.event.PointEventFixture.*;
import static com.triple.pointservice.domain.event.PointEventType.*;
import static com.triple.pointservice.domain.event.PointEventsFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("리뷰 삭제에 대한 포인트를 계산해 이벤트를 생성한다")
class PointDeleteCalculatorTest {
    private PointEventCalculator pointEventCalculator;
    private PointPolicy pointPolicy;

    @BeforeEach
    void setUp() {
        pointEventCalculator = new PointDeleteEventCalculator();
        pointPolicy = new DefaultPointPolicy();
    }

    @DisplayName("텍스트 리뷰를 삭제하면 텍스트 삭제 이벤트를 추가한다")
    @Test
    void deleteText() {
        // given
        PointEvents savedEvents = createPointEvents(
                createTextEvent(ADD, pointPolicy, LocalDate.now())
        );
        Review review = createTextReview(ReviewEventAction.DELETE);
        PointEventCalculateCondition condition = savedEventsCondition(savedEvents);

        // when
        PointEvents events = pointEventCalculator.calculate(review, pointPolicy, condition);

        // then
        assertAll(
                () -> assertThat(getSize(events)).isEqualTo(1),
                () -> assertThat(getTypes(events)).containsOnly(WRITE_TEXT),
                () -> assertThat(getActions(events)).containsOnly(DELETE)
        );
    }

    @DisplayName("사진 리뷰를 삭제하면 사진 삭제 이벤트를 추가한다")
    @Test
    void deletePhoto() {
        // given
        PointEvents savedEvents = createPointEvents(
                createPhotoEvent(ADD, pointPolicy, LocalDate.now())
        );
        Review review = createPhotoReview(ReviewEventAction.DELETE);
        PointEventCalculateCondition condition = savedEventsCondition(savedEvents);

        // when
        PointEvents events = pointEventCalculator.calculate(review, pointPolicy, condition);

        // then
        assertAll(
                () -> assertThat(getSize(events)).isEqualTo(1),
                () -> assertThat(getTypes(events)).containsOnly(ATTACHED_PHOTOS),
                () -> assertThat(getActions(events)).containsOnly(DELETE)
        );
    }

    @DisplayName("텍스트, 사진 리뷰를 삭제하면 텍스트, 사진 삭제 이벤트를 추가한다")
    @Test
    void deleteTextPhoto() {
        // given
        PointEvents savedEvents = createPointEvents(
                createTextEvent(ADD, pointPolicy, LocalDate.of(2022, 6, 1)),
                createPhotoEvent(ADD, pointPolicy, LocalDate.of(2022, 6, 2))
        );
        Review review = createTextAndPhotoReview(ReviewEventAction.DELETE);
        PointEventCalculateCondition condition = savedEventsCondition(savedEvents);

        // when
        PointEvents events = pointEventCalculator.calculate(review, pointPolicy, condition);

        // then
        assertAll(
                () -> assertThat(getSize(events)).isEqualTo(2),
                () -> assertThat(getTypes(events)).containsOnly(WRITE_TEXT, ATTACHED_PHOTOS),
                () -> assertThat(getActions(events)).containsOnly(DELETE)
        );
    }

    @DisplayName("텍스트, 장소 리뷰를 삭제하면 텍스트, 장소 삭제 이벤트를 추가한다")
    @Test
    void deleteTextPlace() {
        // given
        PointEvents savedEvents = createPointEvents(
                createTextEvent(ADD, pointPolicy, LocalDate.of(2022, 6, 1)),
                createPlaceEvent(ADD, pointPolicy, LocalDate.of(2022, 6, 2))
        );
        Review review = createTextReview(ReviewEventAction.DELETE);
        PointEventCalculateCondition condition = savedEventsCondition(savedEvents);

        // when
        PointEvents events = pointEventCalculator.calculate(review, pointPolicy, condition);

        // then
        assertAll(
                () -> assertThat(getSize(events)).isEqualTo(2),
                () -> assertThat(getTypes(events)).containsOnly(WRITE_TEXT, ADDED_FIRST_REVIEW_ON_PLACE),
                () -> assertThat(getActions(events)).containsOnly(DELETE)
        );
    }

    @DisplayName("사진, 장소 리뷰를 삭제하면 사진, 장소 삭제 이벤트를 추가한다")
    @Test
    void deletePhotoPlace() {
        // given
        PointEvents savedEvents = createPointEvents(
                createPhotoEvent(ADD, pointPolicy, LocalDate.of(2022, 6, 1)),
                createPlaceEvent(ADD, pointPolicy, LocalDate.of(2022, 6, 2))
        );
        Review review = createPhotoReview(ReviewEventAction.DELETE);
        PointEventCalculateCondition condition = savedEventsCondition(savedEvents);

        // when
        PointEvents events = pointEventCalculator.calculate(review, pointPolicy, condition);

        // then
        assertAll(
                () -> assertThat(getSize(events)).isEqualTo(2),
                () -> assertThat(getTypes(events)).containsOnly(ATTACHED_PHOTOS, ADDED_FIRST_REVIEW_ON_PLACE),
                () -> assertThat(getActions(events)).containsOnly(DELETE)
        );
    }

    @DisplayName("텍스트, 사진, 장소 리뷰를 삭제하면 텍스트, 사진, 장소 삭제 이벤트를 추가한다")
    @Test
    void deleteTextPhotoPlace() {
        // given
        PointEvents savedEvents = createPointEvents(
                createTextEvent(ADD, pointPolicy, LocalDate.of(2022, 6, 1)),
                createPlaceEvent(ADD, pointPolicy, LocalDate.of(2022, 6, 1)),
                createPhotoEvent(ADD, pointPolicy, LocalDate.of(2022, 6, 2))
        );
        Review review = createTextAndPhotoReview(ReviewEventAction.DELETE);
        PointEventCalculateCondition condition = savedEventsCondition(savedEvents);

        // when
        PointEvents events = pointEventCalculator.calculate(review, pointPolicy, condition);

        // then
        assertAll(
                () -> assertThat(getSize(events)).isEqualTo(3),
                () -> assertThat(getTypes(events))
                        .containsOnly(WRITE_TEXT, ATTACHED_PHOTOS, ADDED_FIRST_REVIEW_ON_PLACE),
                () -> assertThat(getActions(events)).containsOnly(DELETE)
        );
    }

    @DisplayName("리뷰에 대한 이벤트가 등록되어 있지 않으면 예외가 발생한다")
    @Test
    void emptySavedEvents() {
        // given
        Review review = createTextReview(ReviewEventAction.DELETE);
        PointEventCalculateCondition condition = new PointEventCalculateCondition(new PointEvents(), new PointEvents());

        // when, then
        assertThatThrownBy(() -> {
            pointEventCalculator.calculate(review, pointPolicy, condition);
        }).isInstanceOf(PointEventNotFoundException.class);
    }

    @DisplayName("리뷰에 대한 이벤트가 모두 삭제되어 있으면 예외가 발생한다")
    @Test
    void deletedSavedEvents() {
        // given
        PointEvents savedEvents = createPointEvents(
                createTextEvent(ADD, pointPolicy, LocalDate.of(2022, 6, 1)),
                createPlaceEvent(ADD, pointPolicy, LocalDate.of(2022, 6, 1)),
                createPhotoEvent(ADD, pointPolicy, LocalDate.of(2022, 6, 2)),
                createTextEvent(DELETE, pointPolicy, LocalDate.of(2022, 6, 3)),
                createPlaceEvent(DELETE, pointPolicy, LocalDate.of(2022, 6, 3)),
                createPhotoEvent(DELETE, pointPolicy, LocalDate.of(2022, 6, 3))
        );
        Review review = createTextReview(ReviewEventAction.DELETE);
        PointEventCalculateCondition condition = savedEventsCondition(savedEvents);

        // when, then
        assertThatThrownBy(() -> {
            pointEventCalculator.calculate(review, pointPolicy, condition);
        }).isInstanceOf(PointEventAllDeletedException.class);
    }
}
