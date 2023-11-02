package com.backend.projectapi.controller;

import com.backend.projectapi.exception.RecordNotFoundException;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.repository.KorisniciRepository;
import com.backend.projectapi.repository.PrijaveRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BaseController {

    @Autowired
    PrijaveRepository repo;

    @GetMapping("/home")
    public ResponseEntity<Object> getUsers(){
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/home/child_prijave")
    public ResponseEntity<Object> getChildPrijave(@RequestBody Prijava prijava){
        Optional<Prijava> parent_prijava= repo.findById(prijava.getId());
        if (parent_prijava.isEmpty()){
            throw new RecordNotFoundException("nepostoji prijava za dani id: "+prijava.getId());
        }else {
            return new ResponseEntity<>(repo.findAllByParentPrijava(prijava), HttpStatus.OK);
        }
    }
}
