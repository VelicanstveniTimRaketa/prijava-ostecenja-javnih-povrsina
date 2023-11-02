package com.backend.projectapi.service;

import com.backend.projectapi.model.Vijece;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VijeceService {
    public ResponseEntity<List<Vijece>> getAllVijeca();
}
