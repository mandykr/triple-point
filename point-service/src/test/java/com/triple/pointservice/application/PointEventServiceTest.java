package com.triple.pointservice.application;

import com.triple.pointservice.application.dto.PointEventResponse;
import com.triple.pointservice.application.dto.ReviewEventRequest;
import com.triple.pointservice.domain.DefaultPointPolicy;
import com.triple.pointservice.domain.PointEventInMemoryRepository;
import com.triple.pointservice.domain.PointPolicy;
import com.triple.pointservice.domain.repository.PointEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.triple.pointservice.application.PointEventResponseFixture.getActions;
import static com.triple.pointservice.application.PointEventResponseFixture.getTypes;
import static com.triple.pointservice.application.ReviewEventRequestFixture.*;
import static com.triple.pointservice.domain.event.PointEventAction.ADD;
import static com.triple.pointservice.domain.event.PointEventAction.DELETE;
import static com.triple.pointservice.domain.event.PointEventFixture.*;
import static com.triple.pointservice.domain.event.PointEventType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PointEventServiceTest {
    private PointEventRepository pointEventRepository;
    private PointPolicy pointPolicy;
    private PointEventService pointEventService;

    @BeforeEach
    void setUp() {
        pointEventRepository = new PointEventInMemoryRepository();
        pointPolicy = new DefaultPointPolicy();
        pointEventService = new PointEventService(pointEventRepository, pointPolicy);
    }

    @DisplayName("리뷰를 등록하면 리뷰 등록 이벤트를 추가한다")
    @Test
    void add() {
        // given
        ReviewEventRequest request = createReviewAddRequest();

        // when
        List<PointEventResponse> responses = pointEventService.save(request);

        // then
        assertAll(
                () -> assertThat(responses).hasSize(3),
                () -> assertThat(getTypes(responses))
                        .containsOnly(WRITE_TEXT, ATTACHED_PHOTOS, ADDED_FIRST_REVIEW_ON_PLACE),
                () -> assertThat(getActions(responses)).containsOnly(ADD)
        );
    }

    @DisplayName("텍스트 리뷰에 사진을 등록하면 사진 추가 이벤트를 추가한다")
    @Test
    void addPhoto() {
        // given
        pointEventRepository.save(createTextEvent(ADD, pointPolicy, LocalDate.now()));
        ReviewEventRequest request = createReviewModRequest();

        // when
        List<PointEventResponse> responses = pointEventService.save(request);

        // then
        assertAll(
                () -> assertThat(responses).hasSize(1),
                () -> assertThat(getTypes(responses)).containsOnly(ATTACHED_PHOTOS),
                () -> assertThat(getActions(responses)).containsOnly(ADD)
        );
    }

    @DisplayName("사진 리뷰에 텍스트를 등록하면 텍스트 작성 이벤트를 추가한다")
    @Test
    void addText() {
        // given
        pointEventRepository.save(createPhotoEvent(ADD, pointPolicy, LocalDate.now()));
        ReviewEventRequest request = createReviewModRequest();

        // when
        List<PointEventResponse> responses = pointEventService.save(request);

        // then
        assertAll(
                () -> assertThat(responses).hasSize(1),
                () -> assertThat(getTypes(responses)).containsOnly(WRITE_TEXT),
                () -> assertThat(getActions(responses)).containsOnly(ADD)
        );
    }

    @DisplayName("텍스트, 사진 리뷰에서 텍스트를 삭제하면 텍스트 삭제 이벤트를 추가한다")
    @Test
    void deleteContent() {
        // given
        pointEventRepository.save(createTextEvent(ADD, pointPolicy, LocalDate.now()));
        pointEventRepository.save(createPhotoEvent(ADD, pointPolicy, LocalDate.now()));
        ReviewEventRequest request = createReviewDeleteContentRequest();

        // when
        List<PointEventResponse> responses = pointEventService.save(request);

        // then
        assertAll(
                () -> assertThat(responses).hasSize(1),
                () -> assertThat(getTypes(responses)).containsOnly(WRITE_TEXT),
                () -> assertThat(getActions(responses)).containsOnly(DELETE)
        );
    }

    @DisplayName("텍스트, 사진 리뷰에서 사진을 삭제하면 사진 삭제 이벤트를 추가한다")
    @Test
    void deletePhoto() {
        // given
        pointEventRepository.save(createTextEvent(ADD, pointPolicy, LocalDate.now()));
        pointEventRepository.save(createPhotoEvent(ADD, pointPolicy, LocalDate.now()));
        ReviewEventRequest request = createReviewDeletePhotoRequest();

        // when
        List<PointEventResponse> responses = pointEventService.save(request);

        // then
        assertAll(
                () -> assertThat(responses).hasSize(1),
                () -> assertThat(getTypes(responses)).containsOnly(ATTACHED_PHOTOS),
                () -> assertThat(getActions(responses)).containsOnly(DELETE)
        );
    }

    @DisplayName("리뷰를 삭제하면 리뷰 삭제 이벤트를 추가한다")
    @Test
    void delete() {
        // given
        pointEventRepository.save(createTextEvent(ADD, pointPolicy, LocalDate.now()));
        pointEventRepository.save(createPhotoEvent(ADD, pointPolicy, LocalDate.now()));
        pointEventRepository.save(createPlaceEvent(ADD, pointPolicy, LocalDate.now()));
        ReviewEventRequest request = createReviewDeleteRequest();

        // when
        List<PointEventResponse> responses = pointEventService.save(request);

        // then
        assertAll(
                () -> assertThat(responses).hasSize(3),
                () -> assertThat(getTypes(responses))
                        .containsOnly(WRITE_TEXT, ATTACHED_PHOTOS, ADDED_FIRST_REVIEW_ON_PLACE),
                () -> assertThat(getActions(responses)).containsOnly(DELETE)
        );
    }
}
