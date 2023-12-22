package com.backend.projectapi.service;

import com.backend.projectapi.DTO.GradskiUredDTO;
import com.backend.projectapi.model.GradskiUred;
import com.backend.projectapi.model.Korisnik;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GradskiUrediService {

    List<GradskiUred> getAll(String active);
    Object addGradskiUred(GradskiUredDTO gradskiUredDTO);

    Object makeActive(Long id);

    Object zahtjevZaUlazak(Long korisnikId, Long uredId);

    List<Korisnik> sviZahtjevi();

    Object potvrdaZahtjeva(Long korisnikId);

    Object odbijanjeZahjteva(Long korisnikId);

    Object odbijiUred(Long id);
}
