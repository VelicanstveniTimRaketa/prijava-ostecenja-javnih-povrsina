package com.backend.projectapi.repository;

import com.backend.projectapi.DTO.GradskiUredDTO;
import com.backend.projectapi.model.GradskiUred;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.TipOstecenja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.Optional;

public interface GradskiUrediRepository extends JpaRepository<GradskiUred, Long> {
    @Query(value = "select * from gradski_uredi g where g.active = 'false'  ", nativeQuery = true)
    List<GradskiUred> findNeaktiveUrede();
  
    @Query(value = "select * from gradski_uredi g where g.active = 'true'  ", nativeQuery = true)
    List<GradskiUred> findAktivneUrede();
  
    Optional<GradskiUred> findByTipOstecenja(TipOstecenja ostecenje);


}
