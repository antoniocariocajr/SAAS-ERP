package com.billerp.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotNull @Size(min = 3, max = 50) String username,
        @NotNull @Size(min = 3, max = 50) String password) {

}
