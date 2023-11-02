package com.backend.projectapi.service;

import com.backend.projectapi.model.Prijava;
import org.apache.catalina.LifecycleState;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PrijavaService {

    public List<Prijava> getAllPrijave(String status);
    public List<Prijava> getChildPrijave(Long id);
    public Object getClosePrijave(Double latitude, Double longitude);
    Boolean makeChildPrijavu(Long parent_id, Long child_id);
}
