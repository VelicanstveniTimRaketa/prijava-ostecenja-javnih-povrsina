package com.backend.projectapi.service;

import com.backend.projectapi.exception.TokenRefreshException;
import com.backend.projectapi.model.RefreshToken;
import com.backend.projectapi.repository.KorisniciRepository;
import com.backend.projectapi.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private Long refreshTokenDurationMs = (long) (1000 * 60 * 24);


    private final RefreshTokenRepository refreshTokenRepository;
    private final KorisniciRepository korisnikRepo;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, KorisniciRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.korisnikRepo = userRepository;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(korisnikRepo.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(korisnikRepo.findById(userId).get());
    }
}
