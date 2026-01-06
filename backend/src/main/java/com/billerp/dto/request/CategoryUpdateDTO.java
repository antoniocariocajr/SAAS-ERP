package com.billerp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryUpdateDTO(
        @NotNull String id,
        @NotNull @NotBlank String name,
        @NotNull @NotBlank String description) {

}
