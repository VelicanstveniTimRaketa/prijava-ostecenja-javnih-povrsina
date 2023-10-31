package com.backend.projectapi.repository;

import com.backend.projectapi.model.Slika;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlikeRepository extends JpaRepository<Slika, Long> {
}
