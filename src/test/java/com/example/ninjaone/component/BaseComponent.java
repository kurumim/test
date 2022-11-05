package com.example.ninjaone.component;

import com.example.ninjaone.NinjaoneApplication;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@SpringBootTest(
    classes = {NinjaoneApplication.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseComponent {

  @LocalServerPort protected long port;

  protected final Gson gson = new Gson();

  @Before
  public void setUp() {
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssured.baseURI = String.format("http://localhost:%s", port);
  }
}
