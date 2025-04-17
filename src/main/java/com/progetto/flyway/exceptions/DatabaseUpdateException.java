package com.progetto.flyway.exceptions;

public class DatabaseUpdateException extends RuntimeException {
    public DatabaseUpdateException(String message) {
        super(message);
    }
}
