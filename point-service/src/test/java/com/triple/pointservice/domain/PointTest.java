package com.triple.pointservice.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.triple.pointservice.domain.ReviewFixture.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class PointTest {
    @DisplayName("사용자 포인트를 생성한다")
    @Test
    void create() {
        assertThatCode(() -> new Point(UUID.randomUUID(), USER_ID, 3))
                .doesNotThrowAnyException();
    }

    @DisplayName("사용자 포인트를 추가한다")
    @Test
    void add() {
        // given
        Point point = Point.create(UUID.randomUUID(), USER_ID);

        // when
        point.add(3);

        // then
        assertThat(point.getPoint()).isEqualTo(3);
    }

    @DisplayName("사용자 포인트를 회수한다")
    @Test
    void sub() {
        // given
        Point point = new Point(UUID.randomUUID(), USER_ID, 3);

        // when
        point.sub(1);

        // then
        assertThat(point.getPoint()).isEqualTo(2);
    }
}
