package com.billerp.dto.response;

import java.time.LocalDate;

public record CustomerResponse(
        String id,
        String name,
        String email,
        String phone,
        String address,
        boolean active,
        LocalDate createdAt) {
}
