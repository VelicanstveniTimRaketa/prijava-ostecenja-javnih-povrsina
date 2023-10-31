package com.backend.projectapi.controller;

import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.repository.KorisniciRepository;
import com.backend.projectapi.repository.PrijaveRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BaseController {
    @Autowired
    PrijaveRepository repo;

    @GetMapping("/home")
    public ResponseEntity<List<Prijava>> getUsers(){
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }
}
