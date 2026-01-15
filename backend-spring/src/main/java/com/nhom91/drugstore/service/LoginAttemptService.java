package com.nhom91.drugstore.service;

public interface LoginAttemptService {
    int MAX_ATTEMPT = 3;
    long LOCK_TIME_DURATION = 15 * 60;

    void checkLockStatus(String email);
    void loginFailed(String email);
    void loginSucceeded(String email);
    int getAttempts(String email);
    boolean isPermanentlyLocked(String email);
}
