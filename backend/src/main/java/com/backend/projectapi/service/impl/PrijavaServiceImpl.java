package com.backend.projectapi.service.impl;


import com.backend.projectapi.exception.RecordNotFoundException;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.repository.PrijaveRepository;
import com.backend.projectapi.service.PrijavaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrijavaServiceImpl implements PrijavaService {


    private final PrijaveRepository prijaveRepo;

    public PrijavaServiceImpl(PrijaveRepository prijaveRepo){
        this.prijaveRepo=prijaveRepo;
    }


    @Override
    public List<Prijava> getAllPrijave() {
        return prijaveRepo.findAll();
    }

    @Override
    public List<Prijava> getChildPrijave(Long id) {
       Optional<Prijava> optionalPrijava = prijaveRepo.findById(id);
       if (optionalPrijava.isPresent()){
           return prijaveRepo.findAllByParentPrijava(optionalPrijava.get());
       }else {
           return new ArrayList<>();
       }
    }

    @Override
    public List<Prijava> getActivePrijave() {
        return prijaveRepo.getAllByVrijemeOtklonaIsNull();
    }
}
