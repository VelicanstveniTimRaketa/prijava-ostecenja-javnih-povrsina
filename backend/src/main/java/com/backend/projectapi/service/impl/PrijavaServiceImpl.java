package com.backend.projectapi.service.impl;


import com.backend.projectapi.DTO.PrijavaDTO;
import com.backend.projectapi.ResponseData;
import com.backend.projectapi.exception.RecordNotFoundException;
import com.backend.projectapi.model.*;
import com.backend.projectapi.repository.*;
import com.backend.projectapi.response.PrijavaResponse;
import com.backend.projectapi.service.PrijavaService;
import com.backend.projectapi.service.TipOstecenjaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.data.jpa.repository.query.JSqlParserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.security.PrivateKey;
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
    public List<Prijava> getAllPrijave(Long kreatorId, String active, Long parentId, ZonedDateTime dateFrom, ZonedDateTime dateTo, Double lat, Double lng,Long uredId, Long... ostecenjeId) {

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
        if (dateFrom != null && dateTo != null){ // triba dodati ako posalje samo jedan datum da baci error
            listDate=prijaveRepo.findAllByPrvoVrijemePrijaveBetween(dateFrom,dateTo);
            rez.retainAll(listDate);
        }
        List<Prijava> listLokacija = new ArrayList<>();
        if (lat!=null && lng !=null){
            listLokacija=prijaveRepo.findAllByLokacija(lat,lng);
            rez.retainAll(listLokacija);
        }

        List<Prijava> listUred = new ArrayList<>();
        if(uredId != null){
            listUred = prijaveRepo.findAllByGradskiUred(uredId);
            rez.retainAll(listUred);
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
    public Object addPrijave(PrijavaDTO prijavaDTO, HttpServletRequest req, Korisnik kreator) {
        Lokacija lok=new Lokacija(prijavaDTO.getLatitude(), prijavaDTO.getLongitude());
        lokacijRepo.save(lok);
        Korisnik korisnik;
        if (kreator==null){
            korisnik=korisnikRepo.findById(1L).get();
        }else {
            korisnik=kreator;
        }
        Prijava prijava=new Prijava(
                lok,
                prijavaDTO.getNaziv(),
                gradskiUrediRepo.findById(prijavaDTO.getUred()).get(),
                prijavaDTO.getOpis(),
                korisnik,
                null,
                null,
                ZonedDateTime.now(),
                null);

        //Long id = prijaveRepo.save(prijava).getId();
        Prijava prijavaSaved = prijaveRepo.save(prijava);
        if(prijavaDTO.getSlike() != null) {
            List<Slika> savedSlike = addSlike(prijavaDTO.getSlike(), prijavaSaved);
            prijavaSaved.setSlike(savedSlike);
            prijaveRepo.save(prijavaSaved);
        }

        Map<String,Object> returnObj= new HashMap<>();

        returnObj.put("newReport",prijavaSaved);
        returnObj.put("nearbyReports",getClosePrijave(prijavaDTO.getLatitude(), prijavaDTO.getLongitude(), prijavaSaved.getId()));

        return returnObj;
    }


//todo change file creating system in case of adding new photos through updating PRIJAVA
    public List<Slika> addSlike(MultipartFile[] slike, Prijava prijava){
        List<Slika> savedSlike = new LinkedList<>();
        String uploadDirectory = "src/main/resources/static/"+(prijava.getId().toString())+"/";
        try {
            for(MultipartFile slika:slike) {
                String savePath =uploadDirectory+slika.getOriginalFilename();
                File file = new File(savePath);
                file.mkdirs();
                FileUtils.cleanDirectory(file);
                Files.copy(slika.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Slika spremljena na: " + savePath);
                savePath=parseImagePath(savePath);
                savedSlike.add(slikaRepo.save(new Slika(savePath, prijava)));
            }
        } catch (IOException e) {
            throw new RecordNotFoundException("Record not found");
        }
        return savedSlike;
    }

    private static String parseImagePath(String fullPath) {
        // Find the index of "static/" in the path
        int staticIndex = fullPath.indexOf("static/");

        if (staticIndex != -1) {
            // Extract the substring starting from "static/"
            String relativePath = fullPath.substring(staticIndex + "static/".length());
            return relativePath;
        } else {
            // "static/" not found, return the original path
            return fullPath;
        }
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
    public PrijavaResponse findById(Long id) {
        Optional<Prijava> prijava=prijaveRepo.findById(id);
        if (prijava.isPresent()){
            //GradskiUred gradskiUred = gradskiUrediRepo.findByTipOstecenja(prijava.get().getTipOstecenja()).get();
            return new PrijavaResponse(prijava.get().getId(),
                    prijava.get().getNaziv(),
                    prijava.get().getLokacija(),
                    prijava.get().getGradskiUred(),
                    prijava.get().getOpis(),
                    prijava.get().getKreator(),
                    prijava.get().getSlike(),
                    prijava.get().getParentPrijava(),
                    prijava.get().getPrvoVrijemePrijave(),
                    prijava.get().getVrijemeOtklona());
        }else {
            throw new RecordNotFoundException("ne postoji prijava za dani id: "+id);
        }
    }

    @Override
    public boolean deletePrijava(Long id) {
        Optional<Prijava> optionalPrijava = prijaveRepo.findById(id);

        if (optionalPrijava.isPresent()) {
            Prijava currentPrijava = optionalPrijava.get();
            List<Prijava> relatedPrijave = prijaveRepo.findAllByParentPrijava(currentPrijava);
            relatedPrijave.forEach(prijava -> {
                prijava.setParentPrijava(null);
                prijaveRepo.save(prijava);
            });
            prijaveRepo.delete(currentPrijava);
            return true;
        }
        return false;
    }

    @Override
    public Object updatePrijava(Long id, PrijavaDTO prijavaDTO) {
        Optional<Prijava> optionalPrijava=prijaveRepo.findById(id);
        if (optionalPrijava.isEmpty()){
            throw new RecordNotFoundException("ne postoji prijava za dani id: "+id);
        }
        Prijava newPrijava=optionalPrijava.get();

        newPrijava.setNaziv(prijavaDTO.getNaziv());
        newPrijava.setOpis(prijavaDTO.getOpis());
        newPrijava.setGradskiUred(gradskiUrediRepo.findById(prijavaDTO.getUred()).get());
        //newPrijava.setTipOstecenja(gradskiUrediRepo.findById(prijavaDTO.getUred()).get().getTipOstecenja());
        Lokacija lok=new Lokacija(prijavaDTO.getLatitude(), prijavaDTO.getLongitude());
        lokacijRepo.save(lok);
        newPrijava.setLokacija(lok);

        if(prijavaDTO.getSlike() != null) {
            List<Slika> savedSlike = addSlike(prijavaDTO.getSlike(), newPrijava);
            newPrijava.setSlike(savedSlike);
            prijaveRepo.save(newPrijava);
        }
        return true;
    }

    @Override
    public Object dovrsiPrijavu(Long id) {
        Optional<Prijava> prijavaOptional = prijaveRepo.findById(id);
        Prijava prijava;

        if (prijavaOptional.isPresent()){
            prijava=prijavaOptional.get();
        }else{
            throw new RecordNotFoundException("ne postoji prijava za dani id: "+id);
        }

        prijava.setVrijemeOtklona(ZonedDateTime.now());

        return prijaveRepo.save(prijava);

    }

    @Override
    public List<Prijava> getMojePrijave(Long id) {
        return prijaveRepo.findAllByKreatorId(id);
    }

}
