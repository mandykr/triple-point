package com.triple.pointservice.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {
    @Autowired
    Environment environment;

    @BeforeEach
    public void setUp() {
        RestAssured.port = Integer.parseInt(Objects.requireNonNull(environment.getProperty("local.server.port")));
    }
}
