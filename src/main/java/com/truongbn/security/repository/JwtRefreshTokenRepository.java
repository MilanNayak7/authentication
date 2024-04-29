package com.truongbn.security.repository;

import com.truongbn.security.dao.request.JwtRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken,String> {

    Optional<JwtRefreshToken> findByUserId(Long userId);
}
