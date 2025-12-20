package com.example.todo;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class TodoResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
                .when().get("/todos/greeting")
                .then()
                .statusCode(200)
                .body(is("Welcome to the Quarkus Todo API (dev)"));
    }

}