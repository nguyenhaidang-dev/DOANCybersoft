package com.nhom91.drugstore.exception;

import lombok.Getter;

@Getter
public class InvalidCredentialsException extends RuntimeException {
    private final int currentAttempts;
    private final int remainingAttempts;

    public InvalidCredentialsException(String message, int currentAttempts, int remainingAttempts) {
        super(message);
        this.currentAttempts = currentAttempts;
        this.remainingAttempts = remainingAttempts;
    }
}
