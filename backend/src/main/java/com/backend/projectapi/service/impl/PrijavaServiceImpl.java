package com.backend.projectapi.service.impl;


import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.repository.PrijaveRepository;
import com.backend.projectapi.service.PrijavaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    public List<Prijava> getAllPrijave(String status, Long parent_id) {
        if (StringUtils.hasText(status)){
            if (status.equals("true")){
                return prijaveRepo.getAllByVrijemeOtklonaIsNull();
            }
        }else if (parent_id!=null){
            Optional<Prijava> optionalPrijava = prijaveRepo.findById(parent_id);
            if (optionalPrijava.isPresent()){
                return prijaveRepo.findAllByParentPrijava(optionalPrijava.get());
            }else {
                return new ArrayList<>();
            }
        }
        return  prijaveRepo.findAll();

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
    public Object getClosePrijave(Double latitude, Double longitude) {
        List<Prijava> closePrijave= prijaveRepo.findClosePrijave(latitude,longitude);
        if (closePrijave.isEmpty()){
            return new ArrayList<>();
        }
        return closePrijave;
    }

    @Override
    @Transactional
    public Boolean makeChildPrijavu(Long parent_id, Long child_id) {
        Optional<Prijava> parent_prijava=prijaveRepo.findById(parent_id);
        Optional<Prijava> child_prijava=prijaveRepo.findById(child_id);

        if (parent_prijava.isEmpty()){
            return false;
        }
        if (child_prijava.isEmpty()){
            return false;
        }
        Prijava pravi_parent_prijava=null;
        if(parent_prijava.get().getParentPrijava()!=null){ //provjeravamo ima li parent prijava svoju parent prijavu te ako ima nju postavljamo kao parent od childa
            pravi_parent_prijava=parent_prijava.get().getParentPrijava();
        }else{ //ako ne onda je predana parent prijava pravi parent od childa
            pravi_parent_prijava=parent_prijava.get();
        }
        Prijava child=child_prijava.get();
        child.setParentPrijava(pravi_parent_prijava);

        return true;
    }

    @Override
    public boolean deletePrijava(Long id) {
        Optional<Prijava> optionalPrijava = prijaveRepo.findById(id);

        if (optionalPrijava.isPresent()) {
            Prijava prijava = optionalPrijava.get();

            prijaveRepo.delete(prijava);
            return true;
        }

        return false;
    }


}
