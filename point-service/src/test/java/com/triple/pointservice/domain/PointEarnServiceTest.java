package com.triple.pointservice.domain;

import com.triple.pointservice.domain.exception.PointNotFoundException;
import com.triple.pointservice.domain.repository.PointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.triple.pointservice.domain.ReviewFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PointEarnServiceTest {
    private PointRepository pointRepository;
    private PointPolicy pointPolicy;
    private PointEarnService pointEarnService;

    @BeforeEach
    void setUp() {
        pointRepository = new PointInMemoryRepository();
        pointPolicy = new DefaultPointPolicy();
        pointEarnService = new PointEarnService(pointRepository, pointPolicy);
    }

    @DisplayName("사용자의 최초 포인트를 적립한다")
    @Test
    void firstPoint() {
        // when
        Point savePoint = pointEarnService.addPoint(USER_ID);

        // then
        assertThat(savePoint.getPoint()).isEqualTo(1);
    }

    @DisplayName("사용자의 포인트를 추가한다")
    @Test
    void addPoint() {
        // given
        Point point = new Point(UUID.randomUUID(), USER_ID, 3);
        pointRepository.save(point);

        // when
        Point savePoint = pointEarnService.addPoint(USER_ID);

        // then
        assertThat(savePoint.getPoint()).isEqualTo(4);
    }

    @DisplayName("사용자의 포인트를 회수한다")
    @Test
    void subPoint() {
        // given
        Point point = new Point(UUID.randomUUID(), USER_ID, 3);
        pointRepository.save(point);

        // when
        Point savePoint = pointEarnService.subPoint(USER_ID);

        // then
        assertThat(savePoint.getPoint()).isEqualTo(2);
    }

    @DisplayName("사용자 포인트가 저장되어 있지 않으면 포인트 회수 요청에 예외가 발생한다")
    @Test
    void notSavedPoint() {
        assertThatThrownBy(() -> pointEarnService.subPoint(USER_ID))
                .isInstanceOf(PointNotFoundException.class);
    }
}
