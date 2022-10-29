package com.example.ninjaone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class NinjaoneApplication {

  public static void main(String[] args) {
    SpringApplication.run(NinjaoneApplication.class, args);
  }
}
