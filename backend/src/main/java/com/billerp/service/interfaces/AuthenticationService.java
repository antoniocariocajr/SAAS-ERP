package com.billerp.service.interfaces;

import com.billerp.domain.model.User;
import com.billerp.dto.request.LoginRequest;
import com.billerp.dto.response.LoginResponse;

public interface AuthenticationService {
    LoginResponse authenticate(LoginRequest request);

    LoginResponse encode(User user);

    boolean isLoginCorrect(LoginRequest loginRequest, String password);
}
