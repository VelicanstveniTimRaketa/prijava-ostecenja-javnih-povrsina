package com.backend.projectapi.service.impl;


import com.backend.projectapi.exception.RecordNotFoundException;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.repository.PrijaveRepository;
import com.backend.projectapi.service.PrijavaService;
import org.springframework.stereotype.Service;

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
    public Object getChildPrijave(Long id) {
       Optional<Prijava> optionalPrijava = prijaveRepo.findById(id);
       if (optionalPrijava.isEmpty()){
           return new RecordNotFoundException("ne postoji prijava sa danim id-em: "+id);
       }
       return prijaveRepo.findAllByParentPrijava(optionalPrijava.get());
    }

    @Override
    public List<Prijava> getActivePrijave() {
        return prijaveRepo.getAllByVrijemeOtklonaIsNull();
    }

    @Override
    public Object getClosePrijave(Double latitude, Double longitude) {
        List<Prijava> closePrijave= prijaveRepo.findClosePrijave(latitude,longitude);
        if (closePrijave.isEmpty()){
            return new RecordNotFoundException("ne postoje bliske prijave za dane lat "+latitude+" i lng "+longitude);
        }
        return closePrijave;
    }


}
