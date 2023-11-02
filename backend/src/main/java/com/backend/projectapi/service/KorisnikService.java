package com.backend.projectapi.service;

import com.backend.projectapi.model.Korisnik;
import org.apache.coyote.Response;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KorisnikService {
    public List<Korisnik> getAllUsers();
}
