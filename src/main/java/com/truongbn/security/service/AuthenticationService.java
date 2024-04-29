package com.truongbn.security.service;

import com.truongbn.security.dao.request.SignUpRequest;
import com.truongbn.security.dao.request.SigninRequest;
import com.truongbn.security.dao.response.JwtAuthenticationResponse;
import com.truongbn.security.dao.response.UserDTO;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    UserDTO signin(SigninRequest request);
}
