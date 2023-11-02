package com.backend.projectapi.controller;

import com.backend.projectapi.model.TipOstecenja;
import com.backend.projectapi.service.TipOstecenjaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ostecenja/tipovi")
public class TipOstecenjaController {
    private final TipOstecenjaService service;
    public TipOstecenjaController(TipOstecenjaService ostecenjaService){
        this.service = ostecenjaService;
    }

    @GetMapping("")
    public ResponseEntity<List<TipOstecenja>> getOstecenja(){
        return new ResponseEntity<>(service.getAllTipOstecenja(), HttpStatus.OK);
    }
}
