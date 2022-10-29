package com.example.ninjaone.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "service")
public class TypeProperties {

    public List<String> types;

    public List<String> getTypes() {
       return types;
    }

    public void setTypes(final List<String> types) {
        this.types = types;
    }
}
