package com.triple.pointservice.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

public class PointAcceptanceSteps {
    private static final String ENDPOINT = "/points";

    public static ExtractableResponse<Response> 사용자_포인트_조회_요청(UUID userId) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .queryParam("userId", userId)
                .when().get(ENDPOINT)
                .then().log().all().extract();
    }

    public static void 사용자_포인트_조회_완료(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }
}
