package com.backend.projectapi.repository;

import com.backend.projectapi.model.Lokacija;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.model.TipOstecenja;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface PrijaveRepository extends JpaRepository<Prijava, Long> {
    @Query (value = "select prijave.* from prijave where vrijeme_otklona is null", nativeQuery = true)
    List<Prijava> getAllByVrijemeOtklonaIsNull();
    @Query (value = "select prijave.* from prijave where vrijeme_otklona is not null", nativeQuery = true)
    List<Prijava> getAllByVrijemeOtklonaIsNotNull();
    List<Prijava> findAllByParentPrijava(Prijava prijava);

    @Query(value = "select prijave.* from prijave natural join lokacije where " +
            "prvo_vrijeme_prijave between cast (current_timestamp - interval '24 hours' as timestamp) and " +
            "cast (current_timestamp + interval '24 hours' as timestamp) and vrijeme_otklona is null and " +
            "latitude between (:lat - 0.0005) and (:lat + 0.0005) and longitude between (:lng - 0.0005) and " +
            "(:lng + 0.0005) and id != :ID", nativeQuery = true)
    List<Prijava> findClosePrijave (@Param("lat") Double lat, @Param("lng") Double lng, @Param("ID") Long ID);

    @Query(value = "SELECT p.* FROM prijave p JOIN gradski_uredi g ON p.gradski_ured_Id=g.id where g.ostecenje_id= :id",nativeQuery = true)
    List<Prijava> findAllByTipOstecenja(@Param("id") Long id);

    //WHERE timestamp_with_zone AT TIME ZONE 'UTC' = '2023-01-01T12:34:56.789Z'::timestamptz AT TIME ZONE 'UTC';
    @Query(value = "select * from prijave where prvo_vrijeme_prijave AT TIME ZONE 'CET' between :prvoVrijemePrijave and :prvoVrijemePrijave2",nativeQuery = true)
    List<Prijava> findAllByPrvoVrijemePrijaveBetween(@NonNull ZonedDateTime prvoVrijemePrijave, @NonNull ZonedDateTime prvoVrijemePrijave2);

    @Query (value = "select prijave.* from prijave natural join lokacije where latitude between (:lat - 0.0005) and (:lat + 0.0005) and longitude between (:lng - 0.0005) and (:lng + 0.0005)", nativeQuery = true)
    List<Prijava> findAllByLokacija(@Param("lat") Double lat, @Param("lng") Double lng);

    @Query(value = "SELECT * FROM prijave  where kreator_id = :id",nativeQuery = true)
    List<Prijava> findAllByKreatorId(@Param("id") Long id);

}
