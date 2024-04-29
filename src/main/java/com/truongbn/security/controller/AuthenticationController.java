package com.truongbn.security.controller;

import com.truongbn.security.dao.response.UserDTO;
import com.truongbn.security.service.JwtRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.truongbn.security.dao.request.SignUpRequest;
import com.truongbn.security.dao.request.SigninRequest;
import com.truongbn.security.dao.response.JwtAuthenticationResponse;
import com.truongbn.security.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    private JwtRefreshTokenService jwtRefreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<UserDTO> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam String token){
        return  ResponseEntity.ok(jwtRefreshTokenService.generateAccessTokenFromRefreshToken(token));
    }

    @PostMapping("/say")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("hello");
    }
}
