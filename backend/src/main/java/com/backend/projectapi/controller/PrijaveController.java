package com.backend.projectapi.controller;

import com.backend.projectapi.ResponseData;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.service.KorisnikService;
import com.backend.projectapi.service.PrijavaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;


import javax.print.attribute.standard.PrinterIsAcceptingJobs;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PrijaveController {


    private final PrijavaService prijavaService;

    public PrijaveController(PrijavaService prijavaService, KorisnikService korisnikService){
        this.prijavaService=prijavaService;
    }

    // dodaj has_parent --> ? query parametar
    // ostecenjeId ne radi kako treba
    // sortiranje po rasponu datuma --> ? parametar pocetni i zavrsni datum
    // priblizno lokaciji --> lat i long ? query params
    @GetMapping("/prijave")
    public ResponseEntity<ResponseData<List<Prijava>>> getAllPrijave(@RequestParam(required = false) String active,@RequestParam(required = false) Long parent_id,@RequestParam(required = false) Long... ostecenjeId){
        return new ResponseEntity<>(ResponseData.success(prijavaService.getAllPrijave(active,parent_id, ostecenjeId)), HttpStatus.OK);
    }

    @PatchMapping("/makeChild")
    public ResponseEntity<Object> makeChildPrijavu(@RequestParam Long parent_id, @RequestParam Long child_id){
        return new ResponseEntity<>(ResponseData.success(prijavaService.makeChildPrijavu(parent_id,child_id)),HttpStatus.OK);
    }

    @PostMapping("/addPrijava")
    public ResponseEntity<Object> addPrijave(@RequestBody Prijava prijava) {
        Prijava res = prijavaService.addPrijave(prijava);
        if(res == null){
            return new ResponseEntity<>(ResponseData.error(List.of("Invalid params!")), HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(ResponseData.success(prijavaService.getClosePrijave(res.getLokacija().getLatitude(), res.getLokacija().getLongitude(), res.getId())), HttpStatus.OK);
        }
    }

    @DeleteMapping("/deletePrijava")
    public ResponseEntity<Object> deletePrijava(@RequestParam Long id){
        return new ResponseEntity<>(ResponseData.success(prijavaService.deletePrijava(id)), HttpStatus.OK);
    }
}
