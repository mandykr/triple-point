package com.triple.pointservice.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

public class PointEventAcceptanceSteps {
    private static final String ENDPOINT = "/events";

    public static ExtractableResponse<Response> 리뷰_생성_포인트_적립_요청(
            UUID reviewId,
            String content,
            List<UUID> attachedPhotoIds,
            UUID userId,
            UUID placeId) {
        return 포인트_적립_요청("ADD", reviewId, content, attachedPhotoIds, userId, placeId);
    }

    public static ExtractableResponse<Response> 리뷰_수정_포인트_적립_요청(
            UUID reviewId,
            String content,
            List<UUID> attachedPhotoIds,
            UUID userId,
            UUID placeId) {
        return 포인트_적립_요청("MOD", reviewId, content, attachedPhotoIds, userId, placeId);
    }

    private static ExtractableResponse<Response> 포인트_적립_요청(
            String action,
            UUID reviewId,
            String content,
            List<UUID> attachedPhotoIds,
            UUID userId,
            UUID placeId) {

        Map<String, Object> createParams =
                createPointEarnParams(action, reviewId, content, attachedPhotoIds, userId, placeId);
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(createParams)
                .when().post(ENDPOINT)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 리뷰_삭제_포인트_회수_요청(
            UUID reviewId,
            String content,
            List<UUID> attachedPhotoIds,
            UUID userId,
            UUID placeId) {
        return 포인트_적립_요청("DELETE", reviewId, content, attachedPhotoIds, userId, placeId);
    }

    public static void 포인트_적립_완료(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
    }

    public static void 포인트_회수_완료(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
    }

    private static Map<String, Object> createPointEarnParams(
            String action,
            UUID reviewId,
            String content,
            List<UUID> attachedPhotoIds,
            UUID userId,
            UUID placeId) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "REVIEW");
        params.put("action", action);
        params.put("reviewId", reviewId);
        params.put("content", content);
        params.put("attachedPhotoIds", attachedPhotoIds);
        params.put("userId", userId);
        params.put("placeId", placeId);
        return params;
    }
}
