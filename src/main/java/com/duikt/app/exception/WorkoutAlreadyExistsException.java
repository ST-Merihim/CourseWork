package com.duikt.app.exception;

public class WorkoutAlreadyExistsException extends RuntimeException {
    public WorkoutAlreadyExistsException(String message) {
        super(message);
    }
}
