package com.backend.projectapi.service;

import org.apache.coyote.Response;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.ResponseEntity;

public interface KorisnikService {
    public ResponseEntity<Object> getAllUsers();
}
