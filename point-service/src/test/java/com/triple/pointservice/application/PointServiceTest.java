package com.triple.pointservice.application;

import com.triple.pointservice.application.dto.PointResponse;
import com.triple.pointservice.domain.Point;
import com.triple.pointservice.domain.PointInMemoryRepository;
import com.triple.pointservice.domain.repository.PointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.triple.pointservice.domain.ReviewFixture.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;

class PointServiceTest {
    private PointRepository pointRepository;
    private PointService pointService;

    @BeforeEach
    void setUp() {
        pointRepository = new PointInMemoryRepository();
        pointService = new PointService(pointRepository);
    }

    @DisplayName("사용자 포인트를 조회한다")
    @Test
    void findPoint() {
        // given
        Point point = new Point(UUID.randomUUID(), USER_ID, 3);
        pointRepository.save(point);

        // when
        PointResponse findPoint = pointService.findPoint(USER_ID);

        // then
        assertThat(findPoint.getPoint()).isEqualTo(3);
    }

    @DisplayName("사용자 포인트가 저장되어 있지 않으면 빈 값을 반환한다")
    @Test
    void emptyPoint() {
        // when
        PointResponse findPoint = pointService.findPoint(USER_ID);

        // then
        assertThat(findPoint.getId()).isNull();
        assertThat(findPoint.getPoint()).isZero();
    }
}
