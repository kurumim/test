package com.example.ninjaone.component;

import static com.example.ninjaone.constants.TestConstants.WINDOWS;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.example.ninjaone.controller.request.ServiceRequest;
import java.math.BigDecimal;
import org.junit.Test;

public class ServiceComponentTest extends BaseComponent {

  private static final String DEVICES = "/services";
  private static final String MESSAGE = "message";
  private static final String ERRORS = "errors";
  private static final String ENTITY_WITH_ID_100_WAS_NOT_FOUND = "Entity with id 100 was not found";
  private static final String INVALID_REQUEST = "invalid request";
  public static final String DEVICE_ID = "/100";

  @Test
  public void validateNotFoundWhenGetService() {
    given()
        .when()
        .get(DEVICES.concat(DEVICE_ID))
        .then()
        .statusCode(400)
        .body(MESSAGE, is(INVALID_REQUEST))
        .body(ERRORS, hasItem(ENTITY_WITH_ID_100_WAS_NOT_FOUND));
  }

  @Test
  public void validateNotFoundWhenPutService() {
    final var request = new ServiceRequest();
    request.setName("Antivirus");
    request.setCost(BigDecimal.TEN);
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
  public void validateNotFoundWhenPutWithoutNameService() {
    final var request = new ServiceRequest();
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
  public void validateNotFoundWhenPutWithoutTypeService() {
    final var request = new ServiceRequest();
    request.setName("PSA");
    request.setCost(BigDecimal.TEN);
    request.setType("");
    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(request))
        .when()
        .put(DEVICES.concat(DEVICE_ID))
        .then()
        .statusCode(400)
        .body(MESSAGE, is(INVALID_REQUEST))
        .body(ERRORS, hasItem("type: can't be null"));
  }

  @Test
  public void validateNotFoundWhenPutWithoutTypeAndNameService() {
    final var request = new ServiceRequest();
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
        .body(ERRORS, hasItem("type: can't be null"))
        .body(ERRORS, hasItem("cost: can't be null"))
        .body(ERRORS, hasItem("name: can't be empty"));
  }

  @Test
  public void validateNotFoundWhenPutWithInvalidTypeService() {
    final var request = new ServiceRequest();
    request.setName("Backup");
    request.setCost(BigDecimal.TEN);
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
  public void validateNotFoundWhenDeleteService() {
    given()
        .when()
        .delete(DEVICES.concat(DEVICE_ID))
        .then()
        .statusCode(400)
        .body(MESSAGE, is(INVALID_REQUEST))
        .body(ERRORS, hasItem("No class ServiceEntity entity with id 100 exists!"));
  }
}
