package com.example.ninjaone.component;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.example.ninjaone.controller.request.DeviceRequest;
import org.junit.Test;

public class DeviceComponentTest extends BaseComponent {

  @Test
  public void validateNotFoundWhenGetDevice() {
    given()
        .when()
        .get("/devices".concat("/100"))
        .then()
        .statusCode(400)
        .body("message", is("invalid request"))
        .body("errors", hasItem("Entity with id 100 was not found"));
  }

  @Test
  public void validateNotFoundWhenPutDevice() {
    final var request = new DeviceRequest();
    request.setName("Workstation");
    request.setType("Windows");
    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(request))
        .when()
        .put("/devices".concat("/100"))
        .then()
        .statusCode(400)
        .body("message", is("invalid request"))
        .body("errors", hasItem("Entity with id 100 was not found"));
  }

  @Test
  public void validateNotFoundWhenPutWithoutNameDevice() {
    final var request = new DeviceRequest();
    request.setType("Windows");
    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(request))
        .when()
        .put("/devices".concat("/100"))
        .then()
        .statusCode(400)
        .body("message", is("invalid request"))
        .body("errors", hasItem("name: can't be empty"));
  }

  @Test
  public void validateNotFoundWhenPutWithoutTypeDevice() {
    final var request = new DeviceRequest();
    request.setName("Workstation");
    request.setType("");
    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(request))
        .when()
        .put("/devices".concat("/100"))
        .then()
        .statusCode(400)
        .body("message", is("invalid request"))
        .body("errors", hasItem("type: can't be empty"));
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
        .put("/devices".concat("/100"))
        .then()
        .statusCode(400)
        .body("message", is("invalid request"))
        .body("errors", hasItem("type: can't be empty"))
        .body("errors", hasItem("name: can't be empty"));
  }

  @Test
  public void validateNotFoundWhenPutWithInvalidTypeDevice() {
    final var request = new DeviceRequest();
    request.setName("Windows");
    request.setType("IOS");
    given()
        .contentType(io.restassured.http.ContentType.JSON)
        .body(gson.toJson(request))
        .when()
        .put("/devices".concat("/100"))
        .then()
        .statusCode(400)
        .body("message", is("invalid request"))
        .body("errors", hasItem("IOS type is not valid"));
  }

  @Test
  public void validateNotFoundWhenDeleteDevice() {
    given()
        .when()
        .delete("/devices".concat("/100"))
        .then()
        .statusCode(400)
        .body("message", is("invalid request"))
        .body("errors", hasItem("No class DeviceEntity entity with id 100 exists!"));
  }
}
