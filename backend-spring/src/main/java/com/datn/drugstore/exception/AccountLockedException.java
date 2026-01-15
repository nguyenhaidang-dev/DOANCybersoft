package com.datn.drugstore.exception;

import lombok.Getter;

@Getter
public class AccountLockedException extends RuntimeException {
    private final String lockType;
    private final long remainingSeconds;

    public AccountLockedException(String message, String lockType, long remainingSeconds) {
        super(message);
        this.lockType = lockType;
        this.remainingSeconds = remainingSeconds;
    }
}
