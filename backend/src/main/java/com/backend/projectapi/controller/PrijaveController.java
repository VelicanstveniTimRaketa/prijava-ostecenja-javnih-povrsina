package com.backend.projectapi.controller;

import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.service.PrijavaService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PrijaveController {


    private final PrijavaService prijavaService;

    public PrijaveController(PrijavaService prijavaService){
        this.prijavaService=prijavaService;
    }

    @GetMapping("/prijave")
    public ResponseEntity<List<Prijava>> getAllPrijave(){
        return new ResponseEntity<>(prijavaService.getAllPrijave(), HttpStatus.OK);
    }

    @GetMapping("/aktvnePrijave")
    public ResponseEntity<List<Prijava>> getAllActivePrijave(){
        return new ResponseEntity<>(prijavaService.getActivePrijave(),HttpStatus.OK);
    }

    @GetMapping("/childPrijave")
    public ResponseEntity<Object> getChildPrijave(@RequestParam Long id){
        return new ResponseEntity<>(prijavaService.getChildPrijave(id),HttpStatus.OK);
    }
}
