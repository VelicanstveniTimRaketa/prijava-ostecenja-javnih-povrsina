package com.backend.projectapi.repository;

import com.backend.projectapi.model.Lokacija;
import com.backend.projectapi.model.Prijava;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface PrijaveRepository extends JpaRepository<Prijava, Long> {

    List<Prijava> getAllByVrijemeOtklonaIsNull();
    List<Prijava> findAllByParentPrijava(Prijava prijava);

    @Query(value = "select prijave.* from prijave natural join lokacije where " +
            "prvo_vrijeme_prijave between cast (current_timestamp - interval '24 hours' as timestamp) and " +
            "cast (current_timestamp + interval '24 hours' as timestamp) and vrijeme_otklona is null and " +
            "latitude between (:lat - 0.00009) and (:lat + 0.00009) and longitude between (:lng - 0.00009) and " +
            "(:lng + 0.00009)", nativeQuery = true)
    List<Prijava> findClosePrijave (@Param("lat") Double lat, @Param("lng") Double lng);
}
