package com.backend.projectapi.controller;

import com.backend.projectapi.DTO.KorisnikDTO;
import com.backend.projectapi.ResponseData;
import com.backend.projectapi.config.*;
import com.backend.projectapi.exception.ErrorDataResponse;
import com.backend.projectapi.exception.RecordNotFoundException;
import com.backend.projectapi.exception.TokenRefreshException;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.RefreshToken;
import com.backend.projectapi.repository.KorisniciRepository;
import com.backend.projectapi.service.KorisnikService;
import com.backend.projectapi.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class KorisnikController extends ApplicationController {
    private final KorisnikService service;
    private final AuthenticationService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    private final KorisniciRepository korisniciRepo;

    public KorisnikController(KorisnikService service, AuthenticationService authService, AuthenticationManager authenticationManager, JwtService jwtService, RefreshTokenService refreshTokenService, KorisniciRepository korisniciRepo) {
        this.service = service;
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.korisniciRepo = korisniciRepo;

    }

    @GetMapping("/korisnici")
    public ResponseEntity<ResponseData<List<KorisnikDTO>>> getAllUsers(@RequestParam(required = false) Long id) {
        List<KorisnikDTO> korisnikDTOS = new ArrayList<>();
        List<Korisnik> korisnikList = service.getAllUsers(id);

        korisnikList.forEach(korisnik -> korisnikDTOS.add(service.mapToKorisnikDTO(korisnik)));


        return new ResponseEntity<>(ResponseData.success(korisnikDTOS), HttpStatus.OK);

    }

    @PatchMapping("/deleteKorisnici")
    public ResponseEntity<ResponseData<Object>> deleteUser(@RequestParam Long id) {
        return new ResponseEntity<>(ResponseData.success(service.deleteUser(id)), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseData<Object>> register(
            @RequestBody RegisterRequest request
    ) {

        return new ResponseEntity<>(ResponseData.success(authService.register(request)), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseData<Object>> login(
            @RequestBody AuthenticationRequest request
    ) {
        return new ResponseEntity<>(ResponseData.success(authService.authenticate(request)), HttpStatus.OK);
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

    @GetMapping("/me")
    public ResponseEntity<?> currentUser(@RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            String token = authorizationHeader.substring(7);

            try {

                String username = jwtService.extractUsername(token);
                Optional<Korisnik> korisnikOpt = korisniciRepo.findByUsername(username);


                if (korisnikOpt.isPresent()) {
                    Korisnik korisnik = korisnikOpt.get();

                    KorisnikDTO korisnikDTO = service.mapToKorisnikDTO(korisnik);

                    if (!jwtService.isTokenValid(token, korisnik))
                        return new ResponseEntity<>(ResponseData.error(Collections.singletonList("Token expired!")), HttpStatus.UNAUTHORIZED);

                    return new ResponseEntity<>(ResponseData.success(korisnikDTO), HttpStatus.OK);
                }

            } catch (Exception e) {
                return new ResponseEntity<>(ResponseData.error(Collections.singletonList(e.getMessage())), HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(ResponseData.error(Collections.singletonList("Authorization header doesn't exist or it doesn't start with Bearer")), HttpStatus.UNAUTHORIZED);

    }
}