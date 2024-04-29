package com.truongbn.security.service.impl;

import com.truongbn.security.dao.request.JwtRefreshToken;
import com.truongbn.security.dao.response.UserDTO;
import com.truongbn.security.entities.User;
import com.truongbn.security.repository.JwtRefreshTokenRepository;
import com.truongbn.security.repository.UserRepository;
import com.truongbn.security.service.JwtRefreshTokenService;
import com.truongbn.security.service.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class JwtRefreshTokenServiceImpl implements JwtRefreshTokenService {

    @Value("${app.jwt.refresh-expiration-in-ms}")
    private  Long REFRESH_EXPIRATION_IN_MS;

    @Autowired
    private JwtRefreshTokenRepository jwtRefreshTokenRepository;

    @Autowired
    private JwtService jwtService;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    ModelMapper modelMapper;


    @Override
    public JwtRefreshToken jwtRefreshToken(Long userId) {

        JwtRefreshToken jwtRefreshToken = new JwtRefreshToken();

        jwtRefreshToken.setTokenId(UUID.randomUUID().toString());
        jwtRefreshToken.setUserId(userId);
        jwtRefreshToken.setCreateDate(LocalDateTime.now());
        jwtRefreshToken.setExpirationDate(LocalDateTime.now().plus(REFRESH_EXPIRATION_IN_MS, ChronoUnit.MILLIS));

        Optional<JwtRefreshToken> byUserId = jwtRefreshTokenRepository.findByUserId(userId);

        if(byUserId.isPresent()){
            JwtRefreshToken jwtRefreshToken1 = byUserId.get();
            jwtRefreshToken1.setCreateDate(LocalDateTime.now());
            jwtRefreshToken1.setExpirationDate(LocalDateTime.now().plus(REFRESH_EXPIRATION_IN_MS, ChronoUnit.MILLIS));
            return jwtRefreshTokenRepository.save(jwtRefreshToken1);
        }
        return jwtRefreshTokenRepository.save(jwtRefreshToken);
    }


    @Override
    public UserDTO generateAccessTokenFromRefreshToken(String refreshTokenId){
        JwtRefreshToken jwtRefreshToken = jwtRefreshTokenRepository.findById(refreshTokenId).orElseThrow();

        if(jwtRefreshToken.getExpirationDate().isBefore(LocalDateTime.now()))
        {
            throw new RuntimeException("JWT refresh toke is not valid!!! ");
        }

        User user = userRepository.findById(jwtRefreshToken.getUserId()).orElseThrow();


        User userPrincipal = User.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .password(user.getPassword())
                .build();

        String accessToken = jwtService.generateToken(userPrincipal);

        user.setAccessToken(accessToken);
        user.setRefreshToken(refreshTokenId);

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return userDTO;
    }

}
