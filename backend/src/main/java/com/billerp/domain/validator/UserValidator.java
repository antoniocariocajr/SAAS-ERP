package com.billerp.domain.validator;

import org.springframework.stereotype.Component;

import com.billerp.domain.enums.Role;
import com.billerp.domain.exception.UserException;
import com.billerp.domain.exception.UserNotFoundException;
import com.billerp.domain.repository.UserRepository;
import com.billerp.dto.request.UserCreateDTO;
import com.billerp.dto.request.UserUpdateDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    public void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserException("Email already exists", "email");
        }
    }

    public void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UserException("Username already exists", "username");
        }
    }

    public void validateId(String id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id, "id");
        }
    }

    public void validateRole(String id, Role role) {
        validateId(id);
        var user = userRepository.findById(id).orElseThrow();
        if (!user.getRoles().contains(role)) {
            throw new UserException("User does not have role " + role, "role");
        }
        if (user.getRoles().size() == 1) {
            throw new UserException("User cannot have only one role", "role");
        }
    }

    public void validateUpdate(UserUpdateDto dto) {
        validateId(dto.id());
        var user = userRepository.findById(dto.id()).orElseThrow();
        if (!dto.username().equals(user.getUsername())) {
            validateUsername(dto.username());
        }
        if (!dto.email().equals(user.getEmail())) {
            validateEmail(dto.email());
        }
    }

    public void validateCreate(UserCreateDTO dto) {
        validateUsername(dto.username());
        validateEmail(dto.email());
    }

}
