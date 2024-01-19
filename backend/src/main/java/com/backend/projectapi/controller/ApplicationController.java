package com.backend.projectapi.controller;

import com.backend.projectapi.config.JwtService;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.repository.KorisniciRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Controller
public abstract class ApplicationController {
    Korisnik currentUser = null;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private KorisniciRepository korisnikRepo;

    @ModelAttribute("currentUser")
    public Korisnik getCurrentUser(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            String token = authorizationHeader.substring(7);

            try {
                System.out.println("testing");
                String username = jwtService.extractUsername(token);
                Optional<Korisnik> korisnikOpt = korisnikRepo.findByUsername(username);
                Korisnik korisnik = null;
                if (korisnikOpt.isPresent()) {
                    korisnik = korisnikOpt.get();
                    this.currentUser = korisnik;
                }
                return korisnik;
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }
}
