package com.billerp.dto.response;

import java.util.List;

public record UserResponse(
                String id,
                String username,
                String email,
                List<String> roles,
                boolean active) {

}
