package com.backend.projectapi.controller;

import com.backend.projectapi.ResponseData;
import com.backend.projectapi.config.AuthenticationRequest;
import com.backend.projectapi.config.AuthenticationResponse;
import com.backend.projectapi.config.AuthenticationService;
import com.backend.projectapi.config.RegisterRequest;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.service.KorisnikService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class KorisnikController extends ApplicationController {
    private final KorisnikService service;
    private final AuthenticationService authService;
    public KorisnikController(KorisnikService service, AuthenticationService authService){
        this.service = service;
        this.authService = authService;
    }

    @GetMapping("/korisnici")
    public ResponseEntity<ResponseData<List<Korisnik>>> getAllUsers(@RequestParam(required = false) Long id){
        return new ResponseEntity<>(ResponseData.success(service.getAllUsers(id)), HttpStatus.OK);
        //return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @PatchMapping("/deleteKorisnici")
    public ResponseEntity<ResponseData<Object>> deleteUser(@RequestParam Long id){
        return new ResponseEntity<>(ResponseData.success(service.deleteUser(id)),HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseData<Object>> register(
            @RequestBody RegisterRequest request
    ){

        return new ResponseEntity<>(ResponseData.success(authService.register(request)),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseData<Object>> login(
        @RequestBody AuthenticationRequest request
    ){
        return new ResponseEntity<>(ResponseData.success(authService.authenticate(request)),HttpStatus.OK);
    }

}
