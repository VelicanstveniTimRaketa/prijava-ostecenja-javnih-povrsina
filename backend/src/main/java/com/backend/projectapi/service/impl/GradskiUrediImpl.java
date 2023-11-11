package com.backend.projectapi.service.impl;

import com.backend.projectapi.model.GradskiUred;
import com.backend.projectapi.repository.GradskiUrediRepository;
import com.backend.projectapi.service.GradskiUrediService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradskiUrediImpl implements GradskiUrediService {
    private final GradskiUrediRepository vijeceRepo;
    public GradskiUrediImpl(GradskiUrediRepository vijeceRepo){
        this.vijeceRepo = vijeceRepo;
    }

    @Override
    public List<GradskiUred> getAllUredi() {
        return vijeceRepo.findAll();
    }
}
