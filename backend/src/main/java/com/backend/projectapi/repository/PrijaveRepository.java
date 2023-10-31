package com.backend.projectapi.repository;

import com.backend.projectapi.model.Prijava;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrijaveRepository extends JpaRepository<Prijava, Long> {
}
