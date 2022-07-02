package com.triple.pointservice.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.triple.pointservice.acceptance.PointAcceptanceSteps.사용자_포인트_조회_완료;
import static com.triple.pointservice.acceptance.PointAcceptanceSteps.사용자_포인트_조회_요청;

@DisplayName("사용자 포인트를 조회한다")
class PointAcceptanceTest extends AcceptanceTest {
    private UUID userId;

    @BeforeEach
    public void setUp() {
        super.setUp();
        userId = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745");
    }

    /**
     * Feature: 사용자 포인트를 조회한다.
     *
     *   Scenario: 포인트 조회
     *     When 사용자 포인트 조회 요청
     *     Then 사용자 포인트 조회됨
     */
    @Test
    void find() {
        ExtractableResponse<Response> findResponse = 사용자_포인트_조회_요청(userId);
        사용자_포인트_조회_완료(findResponse);
    }
}
