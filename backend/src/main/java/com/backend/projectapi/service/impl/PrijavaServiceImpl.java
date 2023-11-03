package com.backend.projectapi.service.impl;


import com.backend.projectapi.exception.RecordNotFoundException;
import com.backend.projectapi.model.Lokacija;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.model.TipOstecenja;
import com.backend.projectapi.repository.KorisniciRepository;
import com.backend.projectapi.repository.LokacijeRepository;
import com.backend.projectapi.repository.PrijaveRepository;
import com.backend.projectapi.repository.TipoviOstecenjaRepository;
import com.backend.projectapi.service.PrijavaService;
import com.backend.projectapi.service.TipOstecenjaService;
import lombok.NonNull;
import org.springframework.data.jpa.repository.query.JSqlParserUtils;
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
    public List<Prijava> getAllPrijave(String active, Long parentId,Date dateFrom, Date dateTo, Double lat, Double lng, Long... ostecenjeId) {
        List<Prijava> listActive=new ArrayList<>();
        if (StringUtils.hasText(active)){
            if (active.equals("true")){
                listActive.addAll(prijaveRepo.getAllByVrijemeOtklonaIsNull());
            }else if (active.equals("false")){
                listActive.addAll(prijaveRepo.getAllByVrijemeOtklonaIsNotNull());
            }
        }
        List<Prijava> listParent=new ArrayList<>();
        if (parentId != null){
            Optional<Prijava> optionalPrijava = prijaveRepo.findById(parentId);
            if (optionalPrijava.isPresent()){
                listParent.addAll(prijaveRepo.findAllByParentPrijava(optionalPrijava.get()));
            }
        }
        List<Prijava> listOstecenje=new ArrayList<>();
        if(ostecenjeId != null){
            for (Long ostecenje : ostecenjeId) {
                listOstecenje.addAll(prijaveRepo.findAllByTipOstecenja(ostecenje));
            }
        }
        List<Prijava> listDate=new ArrayList<>();
        if (dateFrom!=null && dateTo!=null){ // triba dodati ako posalje samo jedan datum da baci error
            listDate=prijaveRepo.findAllByPrvoVrijemePrijaveBetween(new Timestamp(dateFrom.getTime()),new Timestamp(dateTo.getTime()));
        }
        List<Prijava> listLokacija=new ArrayList<>();
        if (lat!=null && lng !=null){
            listLokacija=prijaveRepo.findAllByLokacija(lat,lng);
        }

        List<Prijava> rez=new ArrayList<>(prijaveRepo.findAll());
        if (!listActive.isEmpty()){
            rez.retainAll(listActive);
        }
        if (!listOstecenje.isEmpty()){
            rez.retainAll(listOstecenje);
        }
        if (!listParent.isEmpty()){
            rez.retainAll(listParent);
        }
        if (!listDate.isEmpty()){
            rez.retainAll(listDate);
        }
        if (!listLokacija.isEmpty()){
            rez.retainAll(listLokacija);
        }
        return rez;


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
    public Prijava findById(Long id) {
        Optional<Prijava> prijava=prijaveRepo.findById(id);
        if (prijava.isPresent()){
            return prijava.get();
        }else {
            throw new RecordNotFoundException("ne postoji prijava za dani id: "+id);
        }
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
