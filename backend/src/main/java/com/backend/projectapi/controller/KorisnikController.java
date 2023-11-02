package com.backend.projectapi.controller;

import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.service.KorisnikService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/korisnici")
public class KorisnikController {
    private final KorisnikService service;
    public KorisnikController(KorisnikService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Korisnik>> getAllUsers(){
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }
}
