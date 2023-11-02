package com.backend.projectapi.repository;

import com.backend.projectapi.model.Prijava;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrijaveRepository extends JpaRepository<Prijava, Long> {

    Optional<List<Prijava>> findAllByParentPrijava(Prijava prijava);
}
