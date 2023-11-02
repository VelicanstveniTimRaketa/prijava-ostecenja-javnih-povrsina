package com.backend.projectapi.controller;

import com.backend.projectapi.ResponseData;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.service.PrijavaService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<ResponseData<List<Prijava>>> getAllPrijave(@RequestParam(required = false) String active){
        System.out.println(prijavaService.getAllPrijave(active));
        return new ResponseEntity<>(ResponseData.success(prijavaService.getAllPrijave(active)), HttpStatus.OK);
    }

    @GetMapping("/childPrijave")
    public ResponseEntity<Object> getChildPrijave(@RequestParam Long id){
        return new ResponseEntity<>(ResponseData.success(prijavaService.getChildPrijave(id)), HttpStatus.OK);
    }

    @PatchMapping("/makeChild")
    public ResponseEntity<Object> makeChildPrijavu(@RequestParam Long parent_id,@RequestParam Long child_id){
        return new ResponseEntity<>(ResponseData.success(prijavaService.makeChildPrijavu(parent_id,child_id)),HttpStatus.OK);
    }

    @GetMapping("/closePrijave")
    public ResponseEntity<Object> getClosePrijave(@RequestParam Double lat, @RequestParam Double lng){
        return new ResponseEntity<>(ResponseData.success(prijavaService.getClosePrijave(lat,lng)), HttpStatus.OK);
    }
}
