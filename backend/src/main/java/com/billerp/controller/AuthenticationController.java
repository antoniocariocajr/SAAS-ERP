package com.billerp.controller;

import com.billerp.dto.request.LoginRequest;
import com.billerp.dto.response.LoginResponse;
import com.billerp.service.interfaces.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.billerp.infrastructure.constants.ApiConstants.AUTH_LOGIN;
import static com.billerp.infrastructure.constants.ApiConstants.AUTH_PATH;
import static com.billerp.infrastructure.constants.ApiConstants.CORS_ORIGIN;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(AUTH_PATH)
@CrossOrigin(origins = CORS_ORIGIN)
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Operations related to authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(AUTH_LOGIN)
    @Operation(summary = "Login", description = "Authenticate a user and return a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ResponseStatus(OK)
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authenticationService.authenticate(request);
    }
}
