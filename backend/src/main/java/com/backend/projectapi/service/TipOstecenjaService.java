package com.backend.projectapi.service;

import com.backend.projectapi.model.TipOstecenja;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TipOstecenjaService {
    public List<TipOstecenja> getAllTipOstecenja();
}
