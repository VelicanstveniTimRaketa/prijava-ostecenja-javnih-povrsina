package com.backend.projectapi.repository;

import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Ref;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Query(value = "select * from refreshtoken where user_id=:id ", nativeQuery = true)
    Optional<RefreshToken> findByKorisnikId(@Param("id") Long id);

    @Modifying
    int deleteByUser(Korisnik user);
}
