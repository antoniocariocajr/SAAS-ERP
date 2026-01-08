package com.billerp.service.impl;

import com.billerp.domain.model.User;
import com.billerp.domain.repository.UserRepository;
import com.billerp.dto.request.LoginRequest;
import com.billerp.dto.response.LoginResponse;
import com.billerp.service.interfaces.AuthenticationService;

import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.billerp.domain.exception.UserNotFoundException;
import com.billerp.domain.exception.UserUnauthorizedException;

import static com.billerp.infrastructure.constants.ApiConstants.ISSUER;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UserNotFoundException(request.username(), "username"));
        if (!isLoginCorrect(request, user.getPassword())) {
            throw new UserUnauthorizedException(request.username());
        }
        return encode(user);
    }

    @Override
    public LoginResponse encode(User user) {
        var now = Instant.now();
        var expiresIn = 1200L;
        var scopes = user.getRoles()
                .stream()
                .map(Enum::name)
                .collect(Collectors.joining(" "));
        var claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwtValue);
    }

    @Override
    public boolean isLoginCorrect(LoginRequest loginRequest, String password) {
        return passwordEncoder.matches(loginRequest.password(), password);
    }
}
