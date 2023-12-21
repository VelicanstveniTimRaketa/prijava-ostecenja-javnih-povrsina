package com.backend.projectapi.controller;

import com.backend.projectapi.DTO.GradskiUredDTO;
import com.backend.projectapi.ResponseData;
import com.backend.projectapi.model.GradskiUred;
import com.backend.projectapi.service.GradskiUrediService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GradskiUredController {
    private final GradskiUrediService gradskaVijecaService;
    public GradskiUredController(GradskiUrediService gradskaVijecaService){
        this.gradskaVijecaService = gradskaVijecaService;
    }

    @GetMapping("/uredi")
    public ResponseEntity<ResponseData<List<GradskiUred>>> getAllAktivniUredi(){
        return new ResponseEntity<>(ResponseData.success(gradskaVijecaService.getAll("true")), HttpStatus.OK);
    }

    @GetMapping("/urediNeaktivni")
    public ResponseEntity<ResponseData<List<GradskiUred>>> getAllNeaktivniUredi(){
        return new ResponseEntity<>(ResponseData.success(gradskaVijecaService.getAll("false")), HttpStatus.OK);
    }

    @PostMapping("/addGradskiUred")
    public ResponseEntity<ResponseData<Object>> addGradskiUred(@RequestBody GradskiUredDTO gradskiUredDTO){
        return new ResponseEntity<>(ResponseData.success(gradskaVijecaService.addGradskiUred(gradskiUredDTO)),HttpStatus.OK);
    }

    @PatchMapping("/makeActive")
    public ResponseEntity<ResponseData<Object>> makeActive(@RequestParam Long id){
        return new ResponseEntity<>(ResponseData.success(gradskaVijecaService.makeActive(id)),HttpStatus.OK);
    }
}
