package com.backend.projectapi.config;

import com.backend.projectapi.ResponseData;
import com.backend.projectapi.exception.RecordNotFoundException;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.RefreshToken;
import com.backend.projectapi.model.Role;
import com.backend.projectapi.repository.KorisniciRepository;
import com.backend.projectapi.repository.RefreshTokenRepository;
import com.backend.projectapi.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
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
        Korisnik korisnik1 = korisnikRepo.save(korisnik);
        var jwtToken = jwtService.generateToken(korisnik);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(korisnik1.getId());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken.getToken())
                .korisnik(korisnikRepo.findByEmail(req.getEmail()).get())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        }catch (AuthenticationException exc){
            throw new RecordNotFoundException("Pogrešan email ili lozinka: "+exc.getMessage());

        }

        var korisnik= korisnikRepo.findByUsername(request.getUsername()).orElseThrow();


        var jwtToken = jwtService.generateToken(korisnik);
        RefreshToken refreshToken = refreshTokenRepository.findByKorisnikId(korisnik.getId()).get();

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken.getToken())
                .korisnik(korisnikRepo.findByUsername(request.getUsername()).get())
                .build();
    }
}
