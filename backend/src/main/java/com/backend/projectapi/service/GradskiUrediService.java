package com.backend.projectapi.service;

import com.backend.projectapi.DTO.GradskiUredDTO;
import com.backend.projectapi.model.GradskiUred;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GradskiUrediService {

    List<GradskiUred> getAll(String active);
    Object addGradskiUred(GradskiUredDTO gradskiUredDTO);

    Object makeActive(Long id);
}
