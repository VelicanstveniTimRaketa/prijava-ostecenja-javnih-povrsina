package com.backend.projectapi.service;

import com.backend.projectapi.model.GradskiUred;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GradskiUrediService {
    public List<GradskiUred> getAllUredi();
}
