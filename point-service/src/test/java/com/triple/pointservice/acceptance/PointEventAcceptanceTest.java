package com.triple.pointservice.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.triple.pointservice.acceptance.PointEventAcceptanceSteps.*;

@DisplayName("포인트를 적립한다")
class PointEventAcceptanceTest extends AcceptanceTest {
    private String content;
    private UUID reviewId;
    private List<UUID> attachedPhotoIds;
    private UUID userId;
    private UUID placeId;

    @BeforeEach
    public void setUp() {
        super.setUp();
        content = "좋아요!";
        reviewId = UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667772");
        attachedPhotoIds = Arrays.asList(
                UUID.fromString("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"),
                UUID.fromString("afb0cef2-851d-4a50-bb07-9cc15cbdc332")
        );
        userId = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745");
        placeId = UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f");
    }

    /**
     * Feature: 포인트를 적립한다.
     *
     *   Scenario: 포인트 적립
     *     When 텍스트 리뷰 포인트 적립 요청
     *     Then 포인트 적립됨
     *     When 사진 추가 포인트 적립 요청
     *     Then 포인트 적립됨
     *     When 텍스트 삭제 포인트 적립 요청
     *     Then 포인트 적립됨
     *     When 사진 삭제 포인트 적립 요청
     *     Then 포인트 적립됨
     *     When 리뷰 삭제 포인트 회수 요청
     *     Then 포인트 회수됨
     */
    @Test
    void earn() {
        ExtractableResponse<Response> addResponse =
                리뷰_생성_포인트_적립_요청(reviewId, content, Collections.emptyList(), userId, placeId);
        포인트_적립_완료(addResponse);
    }
}
