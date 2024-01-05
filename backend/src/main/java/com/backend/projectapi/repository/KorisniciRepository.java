package com.backend.projectapi.repository;

import com.backend.projectapi.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KorisniciRepository extends JpaRepository<Korisnik, Long> {

    Optional<Korisnik> findByEmail(String email);

    Optional<Korisnik> findByUsername(String username);

    @Query(value = "select * from korisnici k where k.ured_status='pending' ", nativeQuery = true)
    List<Korisnik> findByPendingZahthev();

    @Query(value = "select * from korisnici k where k.ured_status='pending' and k.ured_id= :ured_id ", nativeQuery = true)
    List<Korisnik> findByPendingZahtjevOdredeniUred(@Param("ured_id") Long ured_id);
}
