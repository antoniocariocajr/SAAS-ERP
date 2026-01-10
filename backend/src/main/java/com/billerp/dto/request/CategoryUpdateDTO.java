package com.billerp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryUpdateDTO(
                @NotNull(message = "Id cannot be null") @NotBlank(message = "Id cannot be blank") String id,
                @NotNull(message = "Name cannot be null") @NotBlank(message = "Name cannot be blank") String name,
                @NotNull(message = "Description cannot be null") @NotBlank(message = "Description cannot be blank") String description) {

}
