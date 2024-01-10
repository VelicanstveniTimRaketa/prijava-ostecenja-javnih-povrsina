package com.backend.projectapi.controller;

import com.backend.projectapi.DTO.GradskiUredDTO;
import com.backend.projectapi.ResponseData;
import com.backend.projectapi.model.GradskiUred;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.service.GradskiUrediService;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;
import org.hibernate.engine.spi.Resolution;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
public class GradskiUredController extends ApplicationController {
    private final GradskiUrediService gradskaVijecaService;
    public GradskiUredController(GradskiUrediService gradskaVijecaService){
        this.gradskaVijecaService = gradskaVijecaService;
    }

    @GetMapping("/uredi")
    public ResponseEntity<ResponseData<List<GradskiUred>>> getAllAktivniUredi(){
        return new ResponseEntity<>(ResponseData.success(gradskaVijecaService.getAll("true")), HttpStatus.OK);
    }

    @GetMapping("/ured/{id}")
    public ResponseEntity<ResponseData<Object>> getGradskiUred(@PathVariable("id") Long id){
        return new ResponseEntity<>(ResponseData.success(gradskaVijecaService.getUred(id)),HttpStatus.OK);
    }

    @GetMapping("/urediNeaktivni")
    public ResponseEntity<ResponseData<List<GradskiUred>>> getAllNeaktivniUredi(){
        return new ResponseEntity<>(ResponseData.success(gradskaVijecaService.getAll("false")), HttpStatus.OK);
    }

    @PostMapping("/addGradskiUred")
    public ResponseEntity<ResponseData<Object>> addGradskiUred(@RequestBody GradskiUredDTO gradskiUredDTO,@RequestParam(required = false) String tipOstecenjaNaziv){
        return new ResponseEntity<>(ResponseData.success(gradskaVijecaService.addGradskiUred(gradskiUredDTO,currentUser,tipOstecenjaNaziv)),HttpStatus.OK);
    }

    @PatchMapping("/potvrdiUred")
    public ResponseEntity<ResponseData<Object>> potvrdiUred(@RequestParam Long id){
        return new ResponseEntity<>(ResponseData.success(gradskaVijecaService.potvrdiUred(id)),HttpStatus.OK);
    }

    @DeleteMapping("/odbijUred")
    public ResponseEntity<ResponseData<Object>> odbijiUred(@RequestParam Long id){
        return new ResponseEntity<>(ResponseData.success(gradskaVijecaService.odbijiUred(id)),HttpStatus.OK);
    }

    @PatchMapping("/zahtjevZaUlazak")
    public ResponseEntity<ResponseData<Object>> zahtjevZaUlazak(@RequestParam Long uredId){
        return new ResponseEntity<>(ResponseData.success(gradskaVijecaService.zahtjevZaUlazak(currentUser.getId(), uredId)),HttpStatus.OK);
    }

    @GetMapping("/sviZahtjevi")
    public ResponseEntity<ResponseData<List<Korisnik>>> sviZahtjevi(){
        return new ResponseEntity<>(ResponseData.success(gradskaVijecaService.sviZahtjevi()),HttpStatus.OK);
    }

    @GetMapping("/zahtjeviZaOdredeniUred")
    public ResponseEntity<ResponseData<Object>> zahtjeviZaOdredeniUred(){
        return new ResponseEntity<>(ResponseData.success(gradskaVijecaService.zahtjeviZaOdredeniUred(currentUser.getUred(),currentUser.getUred_status())),HttpStatus.OK);
    }

    @PatchMapping("/potvrdaZahtjeva")
    public ResponseEntity<ResponseData<Object>> potvrdaZahtjeva(@RequestParam Long korisnikId){
        return new ResponseEntity<>(ResponseData.success(gradskaVijecaService.potvrdaZahtjeva(korisnikId,currentUser)),HttpStatus.OK);
    }

    @PatchMapping("/odbijanjeZahtjeva")
    public ResponseEntity<ResponseData<Object>> odbijanjeZahtjeva(@RequestParam Long korisnikId){
        return new ResponseEntity<>(ResponseData.success(gradskaVijecaService.odbijanjeZahjteva(korisnikId)),HttpStatus.OK);
    }
}
