package com.backend.projectapi.service;

import com.backend.projectapi.DTO.PrijavaDTO;
import com.backend.projectapi.model.Prijava;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PrijavaService {

    public List<Prijava> getAllPrijave(Long kreatorId, String active, Long parent_id, ZonedDateTime dateFrom, ZonedDateTime dateTo, Double lat, Double lng, Long... ostecenje_id);
    public List<Prijava> getChildPrijave(Long id);
    public Object getClosePrijave(Double latitude, Double longitude, Long ID);
    public Object addPrijave(PrijavaDTO prijavaDTO, HttpServletRequest req);
    Boolean makeChildPrijavu(Long parent_id, Long child_id);
    Prijava findById(Long id);

    boolean deletePrijava(Long id);
}
