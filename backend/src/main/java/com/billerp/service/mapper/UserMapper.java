package com.billerp.service.mapper;

import java.util.EnumSet;

import org.springframework.stereotype.Component;

import com.billerp.domain.enums.Role;
import com.billerp.domain.model.User;
import com.billerp.dto.request.UserCreateDTO;
import com.billerp.dto.response.UserResponse;

@Component
public class UserMapper {
    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream().map(Role::name).toList(),
                user.isActive());
    }

    public User toUser(UserCreateDTO dto, String passwordEncoded) {
        return new User(
                dto.username(),
                passwordEncoded,
                dto.email(),
                EnumSet.of(Role.OPERATOR));
    }

}
