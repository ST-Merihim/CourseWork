package com.duikt.app.exception;

public class GoalAlreadyExistsException extends RuntimeException{
    public GoalAlreadyExistsException(String message) {
        super(message);
    }
}
