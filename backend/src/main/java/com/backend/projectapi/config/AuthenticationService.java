package com.backend.projectapi.config;

import com.backend.projectapi.ResponseData;
import com.backend.projectapi.exception.RecordNotFoundException;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.Role;
import com.backend.projectapi.repository.KorisniciRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final KorisniciRepository korisnikRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest req){
        var korisnik = Korisnik.builder()
                .ime(req.getIme())
                .prezime(req.getPrezime())
                .username(req.getUsername())
                .email(req.getEmail())
                .role(Role.USER)
                .active("true")
                .password(encoder.encode(req.getPassword()))
                .build();

        if(korisnikRepo.findByEmail(req.getEmail()).isPresent()){
            throw new RecordNotFoundException("Korisnik s danim emailom već postoji.");
        }else if(korisnikRepo.findByUsername(req.getUsername()).isPresent()){
            throw new RecordNotFoundException("Korisnik s danim usernameom već postoji.");
        }
        korisnikRepo.save(korisnik);
        var jwtToken = jwtService.generateToken(korisnik);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .korisnik(korisnikRepo.findByEmail(req.getEmail()).get())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        }catch (AuthenticationException exc){
            throw new RecordNotFoundException("Pogrešan email ili lozinka.");
        }

        var korisnik= korisnikRepo.findByEmail(request.getEmail()).orElseThrow();

        // System.out.println(encoder.matches(request.password,korisnik.getPassword()));
        var jwtToken = jwtService.generateToken(korisnik);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .korisnik(korisnikRepo.findByEmail(request.getEmail()).get())
                .build();
    }
}
