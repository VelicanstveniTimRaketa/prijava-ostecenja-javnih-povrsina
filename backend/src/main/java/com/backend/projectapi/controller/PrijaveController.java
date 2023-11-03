package com.backend.projectapi.controller;

import com.backend.projectapi.ResponseData;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.service.PrijavaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // dodat u /prijave sa query opcijom   RESENOOOOOOOO BREEEEEEEE BATOOO BATICEEEEEE
    @GetMapping("/childPrijave")
    public ResponseEntity<Object> getChildPrijave(@RequestParam Long id){
        return new ResponseEntity<>(ResponseData.success(prijavaService.getChildPrijave(id)), HttpStatus.OK);
    }

    // provjeri ako parent_id prijava vec ima parent_prijavu  RESENOOOO BREEEEE
    @PatchMapping("/makeChild")
    public ResponseEntity<Object> makeChildPrijavu(@RequestParam Long parent_id, @RequestParam Long child_id){
        return new ResponseEntity<>(ResponseData.success(prijavaService.makeChildPrijavu(parent_id,child_id)),HttpStatus.OK);
    }

    // todo
    //@PostMapping("/createPrijava")

    // ova logika ce se raditi u postMapingu poslije spremanja u bazu
    @GetMapping("/closePrijave")
    public ResponseEntity<Object> getClosePrijave(@RequestParam Double lat, @RequestParam Double lng){
        return new ResponseEntity<>(ResponseData.success(prijavaService.getClosePrijave(lat,lng)), HttpStatus.OK);
    }
}
