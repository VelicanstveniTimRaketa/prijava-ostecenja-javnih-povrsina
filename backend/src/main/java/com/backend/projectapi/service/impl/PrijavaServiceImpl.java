package com.backend.projectapi.service.impl;


import com.backend.projectapi.model.Lokacija;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.model.TipOstecenja;
import com.backend.projectapi.repository.KorisniciRepository;
import com.backend.projectapi.repository.LokacijeRepository;
import com.backend.projectapi.repository.PrijaveRepository;
import com.backend.projectapi.repository.TipoviOstecenjaRepository;
import com.backend.projectapi.service.PrijavaService;
import com.backend.projectapi.service.TipOstecenjaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PrijavaServiceImpl implements PrijavaService {


    private final PrijaveRepository prijaveRepo;
    private final LokacijeRepository lokacijRepo;
    private final TipoviOstecenjaRepository ostecenjaRepo;
    private final KorisniciRepository korisnikRepo;

    public PrijavaServiceImpl(PrijaveRepository prijaveRepo, LokacijeRepository lokacijRepo, TipoviOstecenjaRepository ostecenjaRepo, KorisniciRepository korisnikRepo){
        this.prijaveRepo = prijaveRepo;
        this.lokacijRepo = lokacijRepo;
        this.ostecenjaRepo = ostecenjaRepo;
        this.korisnikRepo = korisnikRepo;
    }

    @Override
    public List<Prijava> getAllPrijave(String status, Long parent_id, Long[] ostecenje_id) {
        if (StringUtils.hasText(status)){
            if (status.equals("true")){
                return prijaveRepo.getAllByVrijemeOtklonaIsNull();
            }else if (status.equals("false")){
                return prijaveRepo.getAllByVrijemeOtklonaIsNotNull();
            }
        }else if (parent_id != null){
            Optional<Prijava> optionalPrijava = prijaveRepo.findById(parent_id);
            if (optionalPrijava.isPresent()){
                return prijaveRepo.findAllByParentPrijava(optionalPrijava.get());
            }else {
                return new ArrayList<>();
            }
        }else if(ostecenje_id != null){
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
    public Object getClosePrijave(Double latitude, Double longitude, Long ID) {
        List<Prijava> closePrijave= prijaveRepo.findClosePrijave(latitude,longitude, ID);
        if (closePrijave.isEmpty()){
            return new ArrayList<>();
        }
        return closePrijave;
    }

    @Override
    public Prijava addPrijave(Prijava prijava) {
        Lokacija lokacija = lokacijRepo.save(prijava.getLokacija());
        prijava.setLokacija(lokacijRepo.save(prijava.getLokacija()));


        System.out.println(prijava.getSlike());
        Optional<TipOstecenja> tipOstecenja = ostecenjaRepo.findById(prijava.getTipOstecenja().getId());
        if(tipOstecenja.isEmpty()){
            return null;
        }else {
            prijava.setTipOstecenja(tipOstecenja.get());
        }
        prijava.setPrvoVrijemePrijave(new Timestamp(System.currentTimeMillis()));
        // ovaj dio ce se mijenjat kad napravimo autentifikaciju i autrizaciju - tada ce kreator bit
        // trenutno ulogirani korisnik
        prijava.setKreator(korisnikRepo.findById(1L).get());
        return prijaveRepo.save(prijava);
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
