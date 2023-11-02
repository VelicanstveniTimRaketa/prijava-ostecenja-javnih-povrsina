package com.backend.projectapi.service.impl;

import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.repository.KorisniciRepository;
import com.backend.projectapi.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KorisnikServiceImpl implements KorisnikService {

    private final KorisniciRepository korisniciRepo;
    public KorisnikServiceImpl(KorisniciRepository korisniciRepo) {
        this.korisniciRepo = korisniciRepo;
    }
    @Override
    public List<Korisnik> getAllUsers() {
        return korisniciRepo.findAll();
    }
}
