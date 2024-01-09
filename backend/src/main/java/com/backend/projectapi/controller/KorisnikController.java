package com.backend.projectapi.controller;

import com.backend.projectapi.ResponseData;
import com.backend.projectapi.config.*;
import com.backend.projectapi.exception.TokenRefreshException;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.RefreshToken;
import com.backend.projectapi.service.KorisnikService;
import com.backend.projectapi.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class KorisnikController extends ApplicationController {
    private final KorisnikService service;
    private final AuthenticationService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    public KorisnikController(KorisnikService service, AuthenticationService authService,AuthenticationManager authenticationManager,JwtService jwtService,RefreshTokenService refreshTokenService){
        this.service = service;
        this.authService = authService;
        this.authenticationManager= authenticationManager;
        this.jwtService=jwtService;
        this.refreshTokenService= refreshTokenService;

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


    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtService.generateToken(user);
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

}
