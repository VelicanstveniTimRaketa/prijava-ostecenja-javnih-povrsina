package com.backend.projectapi.service;

import com.backend.projectapi.model.Prijava;

import java.util.List;

public interface PrijavaService {

    public List<Prijava> getAllPrijave(String status, Long parent_id);
    public List<Prijava> getChildPrijave(Long id);
    public Object getClosePrijave(Double latitude, Double longitude);
    Boolean makeChildPrijavu(Long parent_id, Long child_id);

    void deletePrijava(Long id);
}
