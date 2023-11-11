package com.backend.projectapi.controller;

import com.backend.projectapi.ResponseData;
import com.backend.projectapi.model.GradskiUred;
import com.backend.projectapi.service.GradskiUrediService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GradskiUredController {
    private final GradskiUrediService gradskaVijecaService;
    public GradskiUredController(GradskiUrediService gradskaVijecaService){
        this.gradskaVijecaService = gradskaVijecaService;
    }

    @GetMapping("/uredi")
    public ResponseEntity<ResponseData<List<GradskiUred>>> getUredi(){
        return new ResponseEntity<>(ResponseData.success(gradskaVijecaService.getAllUredi()), HttpStatus.OK);
    }
}
