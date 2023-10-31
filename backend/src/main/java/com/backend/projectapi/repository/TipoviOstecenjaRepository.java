package com.backend.projectapi.repository;

import com.backend.projectapi.model.TipOstecenja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoviOstecenjaRepository extends JpaRepository<TipOstecenja, Long> {
}
