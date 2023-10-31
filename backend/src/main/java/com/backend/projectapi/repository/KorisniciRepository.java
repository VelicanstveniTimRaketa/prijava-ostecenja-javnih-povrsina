package com.backend.projectapi.repository;

import com.backend.projectapi.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface KorisniciRepository extends JpaRepository<Korisnik, Long> {
}
