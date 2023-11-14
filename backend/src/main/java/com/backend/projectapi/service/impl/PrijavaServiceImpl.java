package com.backend.projectapi.service.impl;


import com.backend.projectapi.DTO.PrijavaDTO;
import com.backend.projectapi.ResponseData;
import com.backend.projectapi.exception.RecordNotFoundException;
import com.backend.projectapi.model.Lokacija;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.model.Slika;
import com.backend.projectapi.model.TipOstecenja;
import com.backend.projectapi.repository.*;
import com.backend.projectapi.service.PrijavaService;
import com.backend.projectapi.service.TipOstecenjaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.data.jpa.repository.query.JSqlParserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.time.LocalDate;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class PrijavaServiceImpl implements PrijavaService {


    private final PrijaveRepository prijaveRepo;
    private final LokacijeRepository lokacijRepo;
    private final GradskiUrediRepository gradskiUrediRepo;
    private final KorisniciRepository korisnikRepo;
    private final SlikeRepository slikaRepo;

    public PrijavaServiceImpl(PrijaveRepository prijaveRepo, LokacijeRepository lokacijRepo, GradskiUrediRepository gradskiUrediRepo, KorisniciRepository korisnikRepo, SlikeRepository slikaRepo){
        this.prijaveRepo = prijaveRepo;
        this.lokacijRepo = lokacijRepo;
        this.gradskiUrediRepo = gradskiUrediRepo;
        this.korisnikRepo = korisnikRepo;
        this.slikaRepo=slikaRepo;
    }

    @Override
    public List<Prijava> getAllPrijave(Long kreatorId, String active, Long parentId, ZonedDateTime dateFrom, ZonedDateTime dateTo, Double lat, Double lng, Long... ostecenjeId) {

        List<Prijava> rez=new ArrayList<>(prijaveRepo.findAll());

        List<Prijava> listKreator=new ArrayList<>();
        if (kreatorId!=null){
            listKreator=prijaveRepo.findAllByKreatorId(kreatorId);
            rez.retainAll(listKreator);
        }
        List<Prijava> listActive=new ArrayList<>();
        if (StringUtils.hasText(active)){
            if (active.equals("true")){
                listActive.addAll(prijaveRepo.getAllByVrijemeOtklonaIsNull());
                rez.retainAll(listActive);
            }else if (active.equals("false")){
                listActive.addAll(prijaveRepo.getAllByVrijemeOtklonaIsNotNull());
                rez.retainAll(listActive);
            }
        }
        List<Prijava> listParent=new ArrayList<>();
        if (parentId != null){
            Optional<Prijava> optionalPrijava = prijaveRepo.findById(parentId);
            if (optionalPrijava.isPresent()){
                listParent.addAll(prijaveRepo.findAllByParentPrijava(optionalPrijava.get()));
                rez.retainAll(listParent);
            }
        }
        List<Prijava> listOstecenje=new ArrayList<>();
        if(ostecenjeId != null){
            for (Long ostecenje : ostecenjeId) {
                listOstecenje.addAll(prijaveRepo.findAllByTipOstecenja(ostecenje));
                rez.retainAll(listOstecenje);
            }
        }
        List<Prijava> listDate=new ArrayList<>();
        if (dateFrom!=null && dateTo!=null){ // triba dodati ako posalje samo jedan datum da baci error
            listDate=prijaveRepo.findAllByPrvoVrijemePrijaveBetween(dateFrom,dateTo);
            rez.retainAll(listDate);
        }
        List<Prijava> listLokacija=new ArrayList<>();
        if (lat!=null && lng !=null){
            listLokacija=prijaveRepo.findAllByLokacija(lat,lng);
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
    public List<Prijava> getClosePrijave(Double latitude, Double longitude, Long ID) {
        List<Prijava> closePrijave= prijaveRepo.findClosePrijave(latitude,longitude, ID);
        if (closePrijave.isEmpty()){
            return new ArrayList<>();
        }
        return closePrijave;
    }

    @Override
    public List<Prijava> addPrijave(PrijavaDTO prijavaDTO, HttpServletRequest req) {
        Lokacija lok=new Lokacija(prijavaDTO.getLatitude(), prijavaDTO.getLongitude());
        lokacijRepo.save(lok);
        Prijava prijava=new Prijava(
                lok,
                prijavaDTO.getNaziv(),
                gradskiUrediRepo.findById(prijavaDTO.getUred()).get().getTipOstecenja(),
                prijavaDTO.getOpis(),
                korisnikRepo.findById(1L).get(),
                null,
                null,
                ZonedDateTime.now(),
                null);

        //Long id = prijaveRepo.save(prijava).getId();
        Prijava prijavaSaved = prijaveRepo.save(prijava);
        if(prijavaDTO.getSlike() != null){
            List<Slika> savedSlike = addSlike(prijavaDTO.getSlike(), prijavaSaved);
            prijavaSaved.setSlike(savedSlike);
            prijaveRepo.save(prijavaSaved);
        }
        return getClosePrijave(prijavaDTO.getLatitude(), prijavaDTO.getLongitude(), prijavaSaved.getId());
    }



    public List<Slika> addSlike(MultipartFile[] slike, Prijava prijava){
        List<Slika> savedSlike = new LinkedList<>();
        String uploadDirectory = "backend/src/main/resources/static/"+(prijava.getId().toString())+"/";
        try {
            for(MultipartFile slika:slike) {
                String savePath =uploadDirectory+slika.getOriginalFilename();
                File file = new File(savePath);
                file.mkdirs();
                Files.copy(slika.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Slika spremljena na: " + savePath);
                savedSlike.add(slikaRepo.save(new Slika(savePath, prijava)));
            }
        } catch (IOException e) {
            throw new RecordNotFoundException("Record not found");
        }
        return savedSlike;
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

    @Override
    public Object updatePrijava(Long id, PrijavaDTO prijavaDTO,MultipartFile[] slike) {
        Optional<Prijava> optionalPrijava=prijaveRepo.findById(id);
        if (optionalPrijava.isEmpty()){
            throw new RecordNotFoundException("ne postoji prijava za dani id: "+id);
        }
        Prijava newPrijava=optionalPrijava.get();

        newPrijava.setNaziv(prijavaDTO.getNaziv());
        newPrijava.setOpis(prijavaDTO.getOpis());
        newPrijava.setTipOstecenja(gradskiUrediRepo.findById(prijavaDTO.getUred()).get().getTipOstecenja());
        Lokacija lok=new Lokacija(prijavaDTO.getLatitude(), prijavaDTO.getLongitude());
        lokacijRepo.save(lok);
        newPrijava.setLokacija(lok);List<Slika> savedSlike = addSlike(prijavaDTO.getSlike(), newPrijava);
        newPrijava.setSlike(savedSlike);

        prijaveRepo.save(newPrijava);
        return true;
    }

}
