package com.backend.projectapi.service;

import com.backend.projectapi.DTO.KorisnikDTO;
import com.backend.projectapi.model.Korisnik;

import java.util.List;

public interface KorisnikService {
    public List<Korisnik> getAllUsers(Long id);

    Boolean addNewKorisnik(Korisnik korisnik);

    Object deleteUser(Long id);

    KorisnikDTO mapToKorisnikDTO(Korisnik korisnik);
}
