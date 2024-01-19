package com.backend.projectapi.service;

import com.backend.projectapi.DTO.GradskiUredDTO;
import com.backend.projectapi.model.GradskiUred;
import com.backend.projectapi.model.Korisnik;


import java.util.List;

public interface GradskiUrediService {

    List<GradskiUred> getAll(String active);

    Object addGradskiUred(GradskiUredDTO gradskiUredDTO, Korisnik kreator, String tipOstecenjaNaziv);

    Object potvrdiUred(Long id);

    Object zahtjevZaUlazak(Long korisnikId, Long uredId);

    List<Korisnik> sviZahtjevi();

    Object potvrdaZahtjeva(Long korisnikId, Korisnik korisnik);

    Object odbijanjeZahjteva(Long korisnikId);

    Object odbijiUred(Long id);

    Object zahtjeviZaOdredeniUred(GradskiUred ured, String uredStatus);

    Object getUred(Long id);
}
