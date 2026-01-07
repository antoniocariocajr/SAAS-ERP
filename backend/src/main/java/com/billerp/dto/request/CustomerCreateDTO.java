package com.billerp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerCreateDTO(
        @NotBlank(message = "Name is required") @NotNull String name,
        @Email(message = "Invalid email format") @NotBlank(message = "Email is required") @NotNull String email,
        @NotBlank(message = "Phone is required") @NotNull String phone,
        String address) {
}
