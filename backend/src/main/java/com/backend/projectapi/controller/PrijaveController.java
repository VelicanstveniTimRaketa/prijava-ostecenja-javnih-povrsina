package com.backend.projectapi.controller;

import com.backend.projectapi.ResponseData;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.service.KorisnikService;
import com.backend.projectapi.service.PrijavaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;


import java.net.SocketTimeoutException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PrijaveController {


    private final PrijavaService prijavaService;
    private final KorisnikService korisnikService;

    public PrijaveController(PrijavaService prijavaService, KorisnikService korisnikService){
        this.prijavaService=prijavaService;
        this.korisnikService = korisnikService;
    }

    @GetMapping("/prijave")
    public ResponseEntity<ResponseData<List<Prijava>>> getAllPrijave(@RequestParam(required = false) String active,@RequestParam(required = false) Long parent_id,@RequestParam(required = false) Long... ostecenje_id){
        return new ResponseEntity<>(ResponseData.success(prijavaService.getAllPrijave(active,parent_id,ostecenje_id)), HttpStatus.OK);
    }

    @PatchMapping("/makeChild")
    public ResponseEntity<Object> makeChildPrijavu(@RequestParam Long parent_id, @RequestParam Long child_id){
        return new ResponseEntity<>(ResponseData.success(prijavaService.makeChildPrijavu(parent_id,child_id)),HttpStatus.OK);
    }

    // ova logika ce se raditi u postMapingu poslije spremanja u bazu
    @GetMapping("/closePrijave")
    public ResponseEntity<Object> getClosePrijave(@RequestParam Double lat, @RequestParam Double lng){
        return new ResponseEntity<>(ResponseData.success(prijavaService.getClosePrijave(lat,lng)), HttpStatus.OK);
    }

    /*
    {
        "lokacija": {
            "longitude": 12345,
            "latitude": 54321
        },
        "tipOstecenja": {
            "id": 2
        },
        "opis": "This is a test description",
        "kreator": {
            "id": 3
        },
        "slike": [],
        "prvoVrijemePrijave": "2023-11-03T10:00:00Z",
        "vrijemeOtklona": null
    }

    */
    @PostMapping("/addPrijava")
    public ResponseEntity<Object> addPrijave(@RequestBody Prijava prijava) {
        // spremanje u bazu
        // dohvacanje close prijava za trenutno spremljenu prijavu
        return new ResponseEntity<>(ResponseData.success(prijavaService.addPrijave(resolvePrijava(prijava))), HttpStatus.OK);
    }

    @DeleteMapping("/deletePrijava")
    public ResponseEntity<Object> deletePrijava(@RequestParam Long id){
        return new ResponseEntity<>(ResponseData.success(prijavaService.deletePrijava(id)), HttpStatus.NO_CONTENT);
    }

    private static Prijava resolvePrijava(Prijava prijava){
        return null;
    }
}
