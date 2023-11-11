package com.backend.projectapi.controller;

import com.backend.projectapi.ResponseData;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.service.KorisnikService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KorisnikController {
    private final KorisnikService service;
    public KorisnikController(KorisnikService service){
        this.service = service;
    }

    @GetMapping("/korisnici")
    public ResponseEntity<ResponseData<List<Korisnik>>> getAllUsers(@RequestParam(required = false) Long id){
        return new ResponseEntity<>(ResponseData.success(service.getAllUsers(id)), HttpStatus.OK);
        //return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @PatchMapping("/deleteKorisnici")
    public ResponseEntity<ResponseData<Object>> deleteUser(@RequestParam Long id){
        return new ResponseEntity<>(ResponseData.success(service.deleteUser(id)),HttpStatus.OK);
    }

    /*

    @PutMapping("/korisnici/add")
    public ResponseEntity<Object> addNewKorisnik(@RequestBody Korisnik korisnik){
        return new ResponseEntity<>(ResponseData.success(service.addNewKorisnik(korisnik)),HttpStatus.OK);
    }

     */
}
