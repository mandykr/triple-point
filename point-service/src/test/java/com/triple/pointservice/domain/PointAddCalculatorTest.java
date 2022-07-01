package com.triple.pointservice.domain;

import com.triple.pointservice.domain.event.PointEvents;
import com.triple.pointservice.domain.event.ReviewEventAction;
import com.triple.pointservice.domain.exception.PointEventAlreadySavedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.triple.pointservice.domain.ReviewFixture.*;
import static com.triple.pointservice.domain.event.PointEventAction.ADD;
import static com.triple.pointservice.domain.event.PointEventAction.DELETE;
import static com.triple.pointservice.domain.event.PointEventFixture.createEvent;
import static com.triple.pointservice.domain.event.PointEventFixture.createTextEvent;
import static com.triple.pointservice.domain.event.PointEventType.*;
import static com.triple.pointservice.domain.event.PointEventsFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("리뷰 추가에 대한 포인트를 계산해 이벤트를 생성한다")
class PointAddCalculatorTest {
    private PointCalculator pointCalculator;
    private PointPolicy pointPolicy;
    private PointEvents savedPlaceEvents;

    @BeforeEach
    void setUp() {
        pointCalculator = new PointAddCalculator();
        pointPolicy = new DefaultPointPolicy();
        savedPlaceEvents = new PointEvents(List.of(
                createEvent(ADDED_FIRST_REVIEW_ON_PLACE, ADD, pointPolicy, LocalDate.now()))
        );
    }

    @DisplayName("리뷰에 1자 이상의 텍스트가 작성되었으면 텍스트 작성 이벤트를 추가한다")
    @Test
    void calculateTextPoint() {
        // given
        Review review = createTextReview(ReviewEventAction.ADD);

        // when
        PointEvents events = pointCalculator.calculate(review, pointPolicy, new PointEvents(), savedPlaceEvents);

        // then
        assertAll(
                () -> assertThat(getSize(events)).isEqualTo(1),
                () -> assertThat(getTypes(events)).containsOnly(WRITE_TEXT),
                () -> assertThat(getActions(events)).containsOnly(ADD)
        );
    }

    @DisplayName("리뷰에 텍스트가 비어있으면 텍스트 작성 이벤트를 추가하지 않는다")
    @ParameterizedTest
    @NullAndEmptySource
    void emptyText(String text) {
        // given
        Review review = createReview(ReviewEventAction.ADD, text, Collections.emptyList());

        // when
        PointEvents events = pointCalculator.calculate(review, pointPolicy, new PointEvents(), savedPlaceEvents);

        // then
        assertThat(getSize(events)).isZero();
    }

    @DisplayName("리뷰에 1장 이상의 사진이 첨부되어 있으면 사진 첨부 이벤트를 추가한다")
    @Test
    void calculatePhotoPoint() {
        // given
        Review review = createPhotoReview(ReviewEventAction.ADD);

        // when
        PointEvents events = pointCalculator.calculate(review, pointPolicy, new PointEvents(), savedPlaceEvents);

        // then
        assertAll(
                () -> assertThat(getSize(events)).isEqualTo(1),
                () -> assertThat(getTypes(events)).containsOnly(ATTACHED_PHOTOS),
                () -> assertThat(getActions(events)).containsOnly(ADD)
        );
    }

    @DisplayName("리뷰에 사진이 첨부되어 있지 않으면 사진 첨부 이벤트를 추가하지 않는다")
    @Test
    void emptyPhoto() {
        // given
        Review review = createReview(ReviewEventAction.ADD, "", Collections.emptyList());

        // when
        PointEvents events = pointCalculator.calculate(review, pointPolicy, new PointEvents(), savedPlaceEvents);

        // then
        assertThat(getSize(events)).isZero();
    }

    @DisplayName("리뷰에 텍스트와 사진이 등록되어 있으면 텍스트 작성 이벤트와 사진 첨부 이벤트를 추가한다")
    @Test
    void calculateContentPoint() {
        // given
        Review review = createReview(ReviewEventAction.ADD, CONTENT, ATTACHED_PHOTO_IDS);

        // when
        PointEvents events = pointCalculator.calculate(review, pointPolicy, new PointEvents(), savedPlaceEvents);

        // then
        assertAll(
                () -> assertThat(getSize(events)).isEqualTo(2),
                () -> assertThat(getTypes(events)).containsOnly(WRITE_TEXT, ATTACHED_PHOTOS),
                () -> assertThat(getActions(events)).containsOnly(ADD)
        );
    }

    @DisplayName("리뷰에 대한 이벤트가 모두 삭제되어 있으면 이벤트가 정상 추가된다")
    @Test
    void alreadySavedAndDeleted() {
        // given
        Review review = createReview(ReviewEventAction.ADD, CONTENT, ATTACHED_PHOTO_IDS);
        PointEvents savedEvents = createPointEvents(
                createTextEvent(DELETE, pointPolicy, LocalDate.now())
        );

        // when
        PointEvents events = pointCalculator.calculate(review, pointPolicy, savedEvents, savedPlaceEvents);

        // then
        assertAll(
                () -> assertThat(getSize(events)).isEqualTo(2),
                () -> assertThat(getTypes(events)).containsOnly(WRITE_TEXT, ATTACHED_PHOTOS),
                () -> assertThat(getActions(events)).containsOnly(ADD)
        );
    }

    @DisplayName("이미 리뷰에 대한 이벤트가 등록되어 있으면 예외가 발생한다")
    @Test
    void alreadySavedContentReview() {
        // given
        Review review = createTextReview(ReviewEventAction.ADD);
        PointEvents savedEvents = createPointEvents(
                createTextEvent(ADD, pointPolicy, LocalDate.now())
        );

        // when, then
        assertThatThrownBy(() -> {
            pointCalculator.calculate(review, pointPolicy, savedEvents, new PointEvents());
        }).isInstanceOf(PointEventAlreadySavedException.class);
    }

    @DisplayName("장소에 처음 등록하는 텍스트 리뷰이면 텍스트 작성, 첫 리뷰 이벤트를 추가한다")
    @Test
    void placeAndTextPoints() {
        // given
        Review review = createTextReview(ReviewEventAction.ADD);

        // when
        PointEvents events = pointCalculator.calculate(review, pointPolicy, new PointEvents(), new PointEvents());

        // then
        assertAll(
                () -> assertThat(getSize(events)).isEqualTo(2),
                () -> assertThat(getTypes(events)).containsOnly(WRITE_TEXT, ADDED_FIRST_REVIEW_ON_PLACE),
                () -> assertThat(getActions(events)).containsOnly(ADD)
        );
    }

    @DisplayName("장소에 처음 등록하는 사진 리뷰이면 사진 첨부, 첫 리뷰 이벤트를 추가한다")
    @Test
    void placeAndPhotoPoints() {
        // given
        Review review = createPhotoReview(ReviewEventAction.ADD);

        // when
        PointEvents events = pointCalculator.calculate(review, pointPolicy, new PointEvents(), new PointEvents());

        // then
        assertAll(
                () -> assertThat(getSize(events)).isEqualTo(2),
                () -> assertThat(getTypes(events)).containsOnly(ATTACHED_PHOTOS, ADDED_FIRST_REVIEW_ON_PLACE),
                () -> assertThat(getActions(events)).containsOnly(ADD)
        );
    }

    @DisplayName("장소에 처음 등록하는 텍스트, 사진 리뷰이면 텍스트 작성, 사진 첨부, 첫 리뷰 이벤트를 추가한다")
    @Test
    void placeAndTextAndPhotoPoints() {
        // given
        Review review = createReview(ReviewEventAction.ADD, CONTENT, ATTACHED_PHOTO_IDS);

        // when
        PointEvents events = pointCalculator.calculate(review, pointPolicy, new PointEvents(), new PointEvents());

        // then
        assertAll(
                () -> assertThat(getSize(events)).isEqualTo(3),
                () -> assertThat(getTypes(events))
                        .containsOnly(WRITE_TEXT, ATTACHED_PHOTOS, ADDED_FIRST_REVIEW_ON_PLACE),
                () -> assertThat(getActions(events)).containsOnly(ADD)
        );
    }

    @DisplayName("장소에 이미 리뷰가 등록되어 있으면 첫 리뷰 이벤트를 추가하지 않는다")
    @Test
    void alreadySavedPlaceReview() {
        // given
        Review review = createReview(ReviewEventAction.ADD, CONTENT, ATTACHED_PHOTO_IDS);

        // when
        PointEvents events = pointCalculator.calculate(review, pointPolicy, new PointEvents(), savedPlaceEvents);

        // then
        assertAll(
                () -> assertThat(getSize(events)).isEqualTo(2),
                () -> assertThat(getTypes(events)).containsOnly(WRITE_TEXT, ATTACHED_PHOTOS),
                () -> assertThat(getActions(events)).containsOnly(ADD)
        );
    }
}
