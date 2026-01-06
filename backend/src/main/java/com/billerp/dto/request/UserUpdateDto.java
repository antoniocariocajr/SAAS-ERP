package com.billerp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdateDto(
                @NotNull @NotBlank String id,
                @NotNull @NotBlank String username,
                @NotNull @NotBlank @Email String email,
                @NotNull @Size(min = 6) String password) {

}
