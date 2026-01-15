package com.datn.drugstore.exception;

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
