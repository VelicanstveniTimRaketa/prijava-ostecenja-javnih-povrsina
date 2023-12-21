package com.backend.projectapi.repository;

import com.backend.projectapi.model.GradskiUred;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.TipOstecenja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradskiUrediRepository extends JpaRepository<GradskiUred, Long> {

    Optional<GradskiUred> findByTipOstecenja(TipOstecenja ostecenje);
}
