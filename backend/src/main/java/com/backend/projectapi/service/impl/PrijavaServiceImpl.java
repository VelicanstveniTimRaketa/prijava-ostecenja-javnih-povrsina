package com.backend.projectapi.service.impl;


import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.model.TipOstecenja;
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
    public List<Prijava> getAllPrijave(String status, Long parent_id, Long[] ostecenje_id) {
        if (StringUtils.hasText(status)){
            if (status.equals("true")){
                return prijaveRepo.getAllByVrijemeOtklonaIsNull();
            }else if (status.equals("false")){
                return prijaveRepo.getAllByVrijemeOtklonaIsNotNull();
            }
        }else if (parent_id!=null){
            Optional<Prijava> optionalPrijava = prijaveRepo.findById(parent_id);
            if (optionalPrijava.isPresent()){
                return prijaveRepo.findAllByParentPrijava(optionalPrijava.get());
            }else {
                return new ArrayList<>();
            }
        }else if(ostecenje_id!=null){
            List<Prijava> list=new ArrayList<>();
            for (Long ostecenje : ostecenje_id) {
                list.addAll(prijaveRepo.findAllByTipOstecenja(ostecenje));
            }
            return list;
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
    public Prijava addPrijave(Prijava prijava) {
        return  prijaveRepo.save(prijava);
    }

    @Override
    @Transactional
    public Boolean makeChildPrijavu(Long parent_id, Long child_id) {
        Optional<Prijava> parent_prijava=prijaveRepo.findById(parent_id);
        Optional<Prijava> child_prijava=prijaveRepo.findById(child_id);

        if (parent_prijava.isEmpty() || child_prijava.isEmpty()){
            return false;
        }
        Prijava parent = parent_prijava.get();
        Prijava child = child_prijava.get();
        child.setParentPrijava(parent.getParentPrijava() != null ? parent.getParentPrijava() : parent);

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
