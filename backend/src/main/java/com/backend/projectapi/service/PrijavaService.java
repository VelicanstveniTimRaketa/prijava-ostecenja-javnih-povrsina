package com.backend.projectapi.service;

import com.backend.projectapi.DTO.PrijavaDTO;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.response.PrijavaResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PrijavaService {

    public List<Prijava> getAllPrijave(Long kreatorId, String active, Long parent_id, ZonedDateTime dateFrom, ZonedDateTime dateTo, Double lat, Double lng,Long uredId, Long... ostecenje_id);
    public List<Prijava> getChildPrijave(Long id);
    public Object getClosePrijave(Double latitude, Double longitude, Long ID);
    public Object addPrijave(PrijavaDTO prijavaDTO, HttpServletRequest req, Korisnik Kreator);
    Boolean makeChildPrijavu(Long parent_id, Long child_id);
    PrijavaResponse findById(Long id);
    boolean deletePrijava(Long id);
    Object updatePrijava(Long id, PrijavaDTO prijavaDTO);

    Object dovrsiPrijavu(Long id);

    List<Prijava> getMojePrijave(Long id);
}
