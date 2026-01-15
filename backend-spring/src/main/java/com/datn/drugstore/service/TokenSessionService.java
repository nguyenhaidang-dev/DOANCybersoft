package com.datn.drugstore.service;

public interface TokenSessionService {
    void saveActiveSession(String email, String token);
    boolean isValidSession(String email, String token);
    void invalidateSession(String email);
}
