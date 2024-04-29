package com.truongbn.security.service.impl;

import com.truongbn.security.dao.response.UserDTO;
import com.truongbn.security.service.JwtRefreshTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.truongbn.security.dao.request.SignUpRequest;
import com.truongbn.security.dao.request.SigninRequest;
import com.truongbn.security.dao.response.JwtAuthenticationResponse;
import com.truongbn.security.entities.Role;
import com.truongbn.security.entities.User;
import com.truongbn.security.repository.UserRepository;
import com.truongbn.security.service.AuthenticationService;
import com.truongbn.security.service.JwtService;

import lombok.RequiredArgsConstructor;

import java.nio.file.attribute.UserPrincipal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private JwtRefreshTokenService jwtRefreshTokenService;
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public UserDTO signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);

        user.setAccessToken(jwt);
        user.setRefreshToken(jwtRefreshTokenService.jwtRefreshToken(user.getId()).getTokenId());

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return userDTO;
    }
}
