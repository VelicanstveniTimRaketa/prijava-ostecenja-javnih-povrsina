package com.backend.projectapi.service.impl;

import com.backend.projectapi.repository.KorisniciRepository;
import com.backend.projectapi.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class KorisnikServiceImpl implements KorisnikService {
    @Autowired
    KorisniciRepository korisniciRepo;

    @Override
    public ResponseEntity<Object> getAllUsers() {
        return new ResponseEntity<>(korisniciRepo.findAll(), HttpStatus.OK);
    }
}
