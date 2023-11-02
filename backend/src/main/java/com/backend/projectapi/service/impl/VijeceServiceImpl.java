package com.backend.projectapi.service.impl;

import com.backend.projectapi.model.Vijece;
import com.backend.projectapi.repository.VijecaRepository;
import com.backend.projectapi.service.VijeceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VijeceServiceImpl implements VijeceService {
    private final VijecaRepository vijeceRepo;
    public VijeceServiceImpl(VijecaRepository vijeceRepo){
        this.vijeceRepo = vijeceRepo;
    }

    @Override
    public ResponseEntity<List<Vijece>> getAllVijeca() {
        return new ResponseEntity<>(vijeceRepo.findAll(), HttpStatus.OK);
    }
}
