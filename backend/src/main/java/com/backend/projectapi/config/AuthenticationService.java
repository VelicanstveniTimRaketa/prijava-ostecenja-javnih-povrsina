package com.backend.projectapi.config;

import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.Role;
import com.backend.projectapi.repository.KorisniciRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                .password(encoder.encode(req.getPassword()))
                .build();

        korisnikRepo.save(korisnik);
        var jwtToken = jwtService.generateToken(korisnik);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        //kad dodemo ode user je auth
        var korisnik= korisnikRepo.findByEmail(request.getEmail()).orElseThrow();//neki error

        var jwtToken = jwtService.generateToken(korisnik);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
