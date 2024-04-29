package com.truongbn.security.service;

import com.truongbn.security.dao.request.JwtRefreshToken;
import com.truongbn.security.dao.response.UserDTO;
import com.truongbn.security.entities.User;

public interface JwtRefreshTokenService {

    JwtRefreshToken jwtRefreshToken(Long userId);

    UserDTO generateAccessTokenFromRefreshToken(String refreshTokenId);
}
