package com.backend.projectapi.service;

import com.backend.projectapi.model.Prijava;
import org.apache.catalina.LifecycleState;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PrijavaService {

    public List<Prijava> getAllPrijave();
    public Object getChildPrijave(Long id);
    public List<Prijava> getActivePrijave();

}
