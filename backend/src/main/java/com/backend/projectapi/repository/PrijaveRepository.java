package com.backend.projectapi.repository;

import com.backend.projectapi.model.Lokacija;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.model.TipOstecenja;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
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
            "latitude between (:lat - 0.00009) and (:lat + 0.00009) and longitude between (:lng - 0.00009) and " +
            "(:lng + 0.00009) and id != :ID", nativeQuery = true)
    List<Prijava> findClosePrijave (@Param("lat") Double lat, @Param("lng") Double lng, @Param("ID") Long ID);

    @Query(value = "SELECT * FROM prijave  WHERE ostecenje_id = :id",nativeQuery = true)
    List<Prijava> findAllByTipOstecenja(@Param("id") Long id);

    @Query(value = "SELECT * FROM prijave where id = :uvjeti",nativeQuery = true)
    List<Prijava> findOvisnoOUvjetu(@Param("uvjeti") String uvjeti);

    @Query(value = "SELECT * FROM prijave  where vrijeme_otklona is null and ostecenje_id = :id",nativeQuery = true)
    List<Prijava> findAllByTipOstecenjaAndActive(@Param("id") Long id);

    @Query(value = "SELECT * FROM prijave  where vrijeme_otklona is not null and ostecenje_id = :id",nativeQuery = true)
    List<Prijava> findAllByTipOstecenjaAndNotActive(@Param("id") Long id);

    List<Prijava> findAllByPrvoVrijemePrijaveBetween(@NonNull Timestamp prvoVrijemePrijave, @NonNull Timestamp prvoVrijemePrijave2);

    @Query (value = "select prijave.* from prijave natural join lokacije where latitude between (:lat - 0.00009) and (:lat + 0.00009) and longitude between (:lng - 0.00009) and (:lng + 0.00009)", nativeQuery = true)
    List<Prijava> findAllByLokacija(@Param("lat") Double lat, @Param("lng") Double lng);

    @Query(value = "SELECT * FROM prijave  where kreator_id = :id",nativeQuery = true)
    List<Prijava> findAllByKreatorId(@Param("id") Long id);
}
