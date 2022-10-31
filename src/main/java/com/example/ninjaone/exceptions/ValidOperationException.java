package com.example.ninjaone.exceptions;

import java.util.Collections;
import java.util.List;

public class ValidOperationException extends RuntimeException {

  private List<String> messages;

  public ValidOperationException(final String message) {
    this.messages = Collections.singletonList(message);
  }

  public ValidOperationException(final List<String> messages) {
    this.messages = messages;
  }

  public List<String> getMessages() {
    return messages;
  }
}
