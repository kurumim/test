package com.example.ninjaone.component;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import com.example.ninjaone.controller.request.ServiceRequest;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class ClientComponentTest extends BaseComponent {

  @Test
  public void validateCostWhenAddDevices() {
    final List<Long> devices = List.of(1L, 2L, 3L);
    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(devices))
        .when()
        .post("/clients/1/devices")
        .then()
        .statusCode(201)
        .body("cost", is(6))
        .body("devices", hasSize(3));
  }

  @Test
  public void validateCostWhenAddServices() {
    final List<Long> devices = List.of(1L, 2L, 3L);
    final List<Long> services = List.of(1L);
    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(devices))
        .when()
        .post("/clients/2/devices")
        .then()
        .statusCode(201)
        .body("cost", is(6))
        .body("devices", hasSize(3));

    final var request = new ServiceRequest();
    request.setName("Antivirus");
    request.setCost(BigDecimal.valueOf(10));
    request.setType("Windows");

    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(request))
        .when()
        .put("/services/1")
        .then()
        .statusCode(200);

    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(services))
        .when()
        .post("/clients/2/services")
        .then()
        .statusCode(201)
        .body("cost", is(36.0F))
        .body("services", hasSize(1));
  }

  @Test
  public void validateCostWhenUpdateServices() {
    final List<Long> devices = List.of(1L, 2L, 3L);
    final List<Long> services = List.of(1L);
    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(devices))
        .when()
        .post("/clients/3/devices")
        .then()
        .statusCode(201)
        .body("cost", is(6))
        .body("devices", hasSize(3));

    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(services))
        .when()
        .post("/clients/3/services")
        .then()
        .statusCode(201)
        .body("cost", is(36.0F))
        .body("services", hasSize(1));

    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(services))
        .when()
        .post("/clients/3/services")
        .then()
        .statusCode(201)
        .body("cost", is(36.0F))
        .body("services", hasSize(1));

    final var request = new ServiceRequest();
    request.setName("Antivirus");
    request.setCost(BigDecimal.valueOf(20));
    request.setType("Windows");

    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(request))
        .when()
        .put("/services/1")
        .then()
        .statusCode(200);

    given()
        .when()
        .get("/clients/3")
        .then()
        .statusCode(200)
        .body("cost", is(66.0F))
        .body("services", hasSize(1));
  }

  @Test
  public void validateCostWhenRemoveServices() {
    final List<Long> devices = List.of(1L, 2L, 3L);
    final List<Long> services = List.of(1L);
    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(devices))
        .when()
        .post("/clients/4/devices")
        .then()
        .statusCode(201)
        .body("cost", is(6))
        .body("devices", hasSize(3));

    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(services))
        .when()
        .post("/clients/4/services")
        .then()
        .statusCode(201)
        .body("cost", is(36.0F))
        .body("services", hasSize(1));

    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(services))
        .when()
        .post("/clients/4/services")
        .then()
        .statusCode(201)
        .body("cost", is(36.0F))
        .body("services", hasSize(1));

    final var request = new ServiceRequest();
    request.setName("Antivirus");
    request.setCost(BigDecimal.valueOf(20));
    request.setType("Windows");

    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(request))
        .when()
        .put("/services/1")
        .then()
        .statusCode(200);

    given()
        .when()
        .get("/clients/4")
        .then()
        .statusCode(200)
        .body("cost", is(66.0F))
        .body("services", hasSize(1));

    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(Collections.EMPTY_LIST))
        .when()
        .post("/clients/4/services")
        .then()
        .statusCode(201)
        .body("cost", is(6))
        .body("services", hasSize(0));
  }

  @Test
  public void validateNotChargeWhenDeviceNotMatchService() {
    final List<Long> devices = List.of(1L, 2L, 3L);
    final List<Long> services = List.of(2L);
    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(devices))
        .when()
        .post("/clients/5/devices")
        .then()
        .statusCode(201)
        .body("cost", is(6))
        .body("devices", hasSize(3));

    final var request = new ServiceRequest();
    request.setName("Antivirus");
    request.setCost(BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(1.25)));
    request.setType("Mac");

    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(request))
        .when()
        .put("/services/2")
        .then()
        .statusCode(200);

    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(services))
        .when()
        .post("/clients/5/services")
        .then()
        .statusCode(201)
        .body("cost", is(6))
        .body("services", hasSize(1));
  }
}
