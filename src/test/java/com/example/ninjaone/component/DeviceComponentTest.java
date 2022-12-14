package com.example.ninjaone.component;

import static com.example.ninjaone.constants.TestConstants.WINDOWS;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.example.ninjaone.controller.request.DeviceRequest;
import org.junit.Test;

public class DeviceComponentTest extends BaseComponent {

  private static final String WORKSTATION = "Workstation";
  private static final String DEVICES = "/devices";
  private static final String MESSAGE = "message";
  private static final String ERRORS = "errors";
  private static final String ENTITY_WITH_ID_100_WAS_NOT_FOUND = "Entity with id 100 was not found";
  private static final String INVALID_REQUEST = "invalid request";
  public static final String DEVICE_ID = "/100";

  @Test
  public void validateNotFoundWhenGetDevice() {
    given()
        .when()
        .get(DEVICES.concat(DEVICE_ID))
        .then()
        .statusCode(400)
        .body(MESSAGE, is(INVALID_REQUEST))
        .body(ERRORS, hasItem(ENTITY_WITH_ID_100_WAS_NOT_FOUND));
  }

  @Test
  public void validateNotFoundWhenPutDevice() {
    final var request = new DeviceRequest();
    request.setName(WORKSTATION);
    request.setType(WINDOWS);
    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(request))
        .when()
        .put(DEVICES.concat(DEVICE_ID))
        .then()
        .statusCode(400)
        .body(MESSAGE, is(INVALID_REQUEST))
        .body(ERRORS, hasItem(ENTITY_WITH_ID_100_WAS_NOT_FOUND));
  }

  @Test
  public void validateNotFoundWhenPutWithoutNameDevice() {
    final var request = new DeviceRequest();
    request.setType(WINDOWS);
    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(request))
        .when()
        .put(DEVICES.concat(DEVICE_ID))
        .then()
        .statusCode(400)
        .body(MESSAGE, is(INVALID_REQUEST))
        .body(ERRORS, hasItem("name: can't be empty"));
  }

  @Test
  public void validateNotFoundWhenPutWithoutTypeDevice() {
    final var request = new DeviceRequest();
    request.setName(WORKSTATION);
    request.setType("");
    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(request))
        .when()
        .put(DEVICES.concat(DEVICE_ID))
        .then()
        .statusCode(400)
        .body(MESSAGE, is(INVALID_REQUEST))
        .body(ERRORS, hasItem("type: can't be empty"));
  }

  @Test
  public void validateNotFoundWhenPutWithoutTypeAndNameDevice() {
    final var request = new DeviceRequest();
    request.setName("");
    request.setType("");
    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(request))
        .when()
        .put(DEVICES.concat(DEVICE_ID))
        .then()
        .statusCode(400)
        .body(MESSAGE, is(INVALID_REQUEST))
        .body(ERRORS, hasItem("type: can't be empty"))
        .body(ERRORS, hasItem("name: can't be empty"));
  }

  @Test
  public void validateNotFoundWhenPutWithInvalidTypeDevice() {
    final var request = new DeviceRequest();
    request.setName(WINDOWS);
    request.setType("IOS");
    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(request))
        .when()
        .put(DEVICES.concat(DEVICE_ID))
        .then()
        .statusCode(400)
        .body(MESSAGE, is(INVALID_REQUEST))
        .body(ERRORS, hasItem("IOS type is not valid"));
  }

  @Test
  public void validateNotFoundWhenDeleteDevice() {
    given()
        .when()
        .delete(DEVICES.concat(DEVICE_ID))
        .then()
        .statusCode(400)
        .body(MESSAGE, is(INVALID_REQUEST))
        .body(ERRORS, hasItem("No class DeviceEntity entity with id 100 exists!"));
  }
}
