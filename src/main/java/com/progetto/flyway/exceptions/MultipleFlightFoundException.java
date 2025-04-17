package com.progetto.flyway.exceptions;

public class MultipleFlightFoundException extends RuntimeException {
    public MultipleFlightFoundException(String message) {

      super(message);
    }
}
