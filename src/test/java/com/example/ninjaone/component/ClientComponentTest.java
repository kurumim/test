package com.example.ninjaone.component;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import com.example.ninjaone.controller.request.DeviceRequest;
import com.example.ninjaone.controller.request.ServiceRequest;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class ClientComponentTest extends BaseComponent {

  public static final String SET_SERVICES_TO_CLIENT_ID = "/clients/%s/services";

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
        .post(String.format(SET_SERVICES_TO_CLIENT_ID, 2))
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
        .post(String.format(SET_SERVICES_TO_CLIENT_ID, 3))
        .then()
        .statusCode(201)
        .body("cost", is(36.0F))
        .body("services", hasSize(1));

    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(services))
        .when()
        .post(String.format(SET_SERVICES_TO_CLIENT_ID, 3))
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
        .post(String.format(SET_SERVICES_TO_CLIENT_ID, 4))
        .then()
        .statusCode(201)
        .body("cost", is(36.0F))
        .body("services", hasSize(1));

    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(services))
        .when()
        .post(String.format(SET_SERVICES_TO_CLIENT_ID, 4))
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
        .post(String.format(SET_SERVICES_TO_CLIENT_ID, 4))
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
        .post(String.format(SET_SERVICES_TO_CLIENT_ID, 5))
        .then()
        .statusCode(201)
        .body("cost", is(6))
        .body("services", hasSize(1));
  }

  @Test
  public void validaCostChangeWhenChangeDevice() {
    final List<Long> devices = List.of(1L, 2L, 3L, 4L);
    final List<Long> services = List.of(2L);
    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(devices))
        .when()
        .post("/clients/6/devices")
        .then()
        .statusCode(201)
        .body("cost", is(8))
        .body("devices", hasSize(4));

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
        .post(String.format(SET_SERVICES_TO_CLIENT_ID, 6))
        .then()
        .statusCode(201)
        .body("cost", is(20.50F))
        .body("services", hasSize(1));

    final var deviceRequest = new DeviceRequest();
    deviceRequest.setType("Windows");
    deviceRequest.setName("MacBook");

    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(deviceRequest))
        .when()
        .put("/devices/4")
        .then()
        .statusCode(200);

    given()
        .when()
        .get("/clients/6")
        .then()
        .statusCode(200)
        .body("cost", is(8.0F))
        .body("services", hasSize(1));
  }
}
