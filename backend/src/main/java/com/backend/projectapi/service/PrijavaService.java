package com.backend.projectapi.service;

import com.backend.projectapi.model.Prijava;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PrijavaService {

    public List<Prijava> getAllPrijave(String active, Long parent_id, Date dateFrom, Date dateTo, Double lat, Double lng, Long... ostecenje_id);
    public List<Prijava> getChildPrijave(Long id);
    public Object getClosePrijave(Double latitude, Double longitude, Long ID);
    public Prijava addPrijave(Prijava prijava);
    Boolean makeChildPrijavu(Long parent_id, Long child_id);
    Prijava findById(Long id);

    boolean deletePrijava(Long id);
}
