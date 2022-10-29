package com.example.ninjaone.controller.exceptions;

import java.util.List;

public class Issue {
    String message;

    List<String> errors;

    public Issue(final String message, final List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(final List<String> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
