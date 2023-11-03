package com.backend.projectapi.controller;

import com.backend.projectapi.ResponseData;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.service.PrijavaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class PrijaveController {


    private final PrijavaService prijavaService;

    public PrijaveController(PrijavaService prijavaService){
        this.prijavaService=prijavaService;
    }

    @GetMapping("/prijave")
    public ResponseEntity<ResponseData<List<Prijava>>> getAllPrijave(@RequestParam(required = false) String active,@RequestParam(required = false) Long parent_id){
        System.out.println(prijavaService.getAllPrijave(active, parent_id));
        return new ResponseEntity<>(ResponseData.success(prijavaService.getAllPrijave(active,parent_id)), HttpStatus.OK);
    }

    // provjeri ako parent_id prijava vec ima parent_prijavu  RESENOOOO BREEEEE
    @PatchMapping("/makeChild")
    public ResponseEntity<Object> makeChildPrijavu(@RequestParam Long parent_id, @RequestParam Long child_id){
        return new ResponseEntity<>(ResponseData.success(prijavaService.makeChildPrijavu(parent_id,child_id)),HttpStatus.OK);
    }

    // ova logika ce se raditi u postMapingu poslije spremanja u bazu
    @GetMapping("/closePrijave")
    public ResponseEntity<Object> getClosePrijave(@RequestParam Double lat, @RequestParam Double lng){
        return new ResponseEntity<>(ResponseData.success(prijavaService.getClosePrijave(lat,lng)), HttpStatus.OK);
    }
  
    @DeleteMapping("/deletePrijava")
    public ResponseEntity<Object> deletePrijava(@RequestParam Long id){
        return new ResponseEntity<>(ResponseData.success(prijavaService.deletePrijava(id)), HttpStatus.NO_CONTENT);
    }
  
    @GetMapping("/addPrijave")
    public ResponseEntity<Object> addPrijave(@RequestBody Prijava prijava) {
        return new ResponseEntity<>(ResponseData.success(prijavaService.addPrijave(prijava)), HttpStatus.OK);
    }
}
