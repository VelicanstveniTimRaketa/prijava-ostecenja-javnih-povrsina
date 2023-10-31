package com.backend.projectapi.repository;

import com.backend.projectapi.model.Lokacija;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LokacijeRepository extends JpaRepository<Lokacija, Long> {
}
