package com.backend.projectapi.service.impl;

import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.model.Slika;
import com.backend.projectapi.repository.SlikeRepository;
import com.backend.projectapi.service.SlikaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SlikaServiceImpl implements SlikaService {

    private final SlikeRepository slikeRepo;
    public SlikaServiceImpl(SlikeRepository slikeRepo){
        this.slikeRepo = slikeRepo;
    }
    @Override
    public void saveImage(String base64Image, Prijava prijava) {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        Slika slika = new Slika(imageBytes, prijava);
        slikeRepo.save(slika);
    }
}
