package com.backend.projectapi.service.impl;

import com.backend.projectapi.model.TipOstecenja;
import com.backend.projectapi.repository.TipoviOstecenjaRepository;
import com.backend.projectapi.service.TipOstecenjaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipOstecenjaImpl implements TipOstecenjaService {
    private final TipoviOstecenjaRepository ostecenjaRepo;

    public TipOstecenjaImpl(TipoviOstecenjaRepository ostecenjaRepo){
        this.ostecenjaRepo = ostecenjaRepo;
    }

    @Override
    public List<TipOstecenja> getAllTipOstecenja() {
        return ostecenjaRepo.findAll();
    }
}
