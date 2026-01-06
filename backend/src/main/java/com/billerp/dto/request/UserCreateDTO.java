package com.billerp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
        @NotNull @NotBlank String username,
        @NotNull @NotBlank @Size(min = 6) String password,
        @NotNull @NotBlank @Email String email) {
}
