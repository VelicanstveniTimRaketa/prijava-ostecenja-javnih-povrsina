package com.backend.projectapi.service.impl;

import com.backend.projectapi.exception.RecordNotFoundException;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.repository.KorisniciRepository;
import com.backend.projectapi.service.KorisnikService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KorisnikServiceImpl implements KorisnikService {

    private final KorisniciRepository korisniciRepo;
    public KorisnikServiceImpl(KorisniciRepository korisniciRepo) {
        this.korisniciRepo = korisniciRepo;
    }
    @Override
    public List<Korisnik> getAllUsers(Long id) {
        if (id!=null){
            Optional<Korisnik> korisnik=korisniciRepo.findById(id);
            if (korisnik.isPresent()){
                List<Korisnik> list=new ArrayList<>();
                list.add(korisnik.get());
                return list;
            }else{
                throw new RecordNotFoundException("ne postoji korisnik za dani id: "+id);
            }
        }else{
            return korisniciRepo.findAll();
        }
    }

    @Override
    public Boolean addNewKorisnik(Korisnik korisnik) {
        Optional<Korisnik> myb_korisnik=korisniciRepo.findById(korisnik.getId());
        if (myb_korisnik.isPresent()){
            //tribalo bi napraviti novi neki error za ovo
            throw new RecordNotFoundException("korisnik sa danim id-em: "+korisnik.getId()+" vec postoji ");
        }else{
            korisniciRepo.save(korisnik);
            return true;
        }
    }
}
