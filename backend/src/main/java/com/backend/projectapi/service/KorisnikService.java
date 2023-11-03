package com.backend.projectapi.service;

import com.backend.projectapi.model.Korisnik;

import java.util.List;

public interface KorisnikService {
    public List<Korisnik> getAllUsers(Long id);

    Boolean addNewKorisnik(Korisnik korisnik);
}
