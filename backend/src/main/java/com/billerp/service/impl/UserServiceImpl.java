package com.billerp.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.billerp.domain.enums.Role;
import com.billerp.domain.repository.UserRepository;
import com.billerp.domain.validator.UserValidator;
import com.billerp.dto.request.UserCreateDTO;
import com.billerp.dto.request.UserUpdateDto;
import com.billerp.dto.response.UserResponse;
import com.billerp.service.interfaces.UserService;
import com.billerp.service.mapper.UserMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserCreateDTO dto) {
        userValidator.validateCreate(dto);
        var user = userMapper.toUser(dto, passwordEncoder.encode(dto.password()));
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toResponse);
    }

    @Override
    public UserResponse getUserById(String id) {
        userValidator.validateId(id);
        return userMapper.toResponse(userRepository.findById(id).orElseThrow());
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        userValidator.validateEmail(email);
        return userMapper.toResponse(userRepository.findByEmail(email).orElseThrow());
    }

    @Override
    public UserResponse updateUser(UserUpdateDto dto) {
        userValidator.validateUpdate(dto);
        var user = userRepository.findById(dto.id()).orElseThrow();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse addRole(String id, Role role) {
        userValidator.validateId(id);
        var user = userRepository.findById(id).orElseThrow();
        user.addRole(role);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse removeRole(String id, Role role) {
        userValidator.validateRole(id, role);
        var user = userRepository.findById(id).orElseThrow();
        user.removeRole(role);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse enableUser(String id) {
        userValidator.validateId(id);
        var user = userRepository.findById(id).orElseThrow();
        user.setActive(true);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse disableUser(String id) {
        userValidator.validateId(id);
        var user = userRepository.findById(id).orElseThrow();
        user.setActive(false);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(String id) {
        userValidator.validateId(id);
        userRepository.deleteById(id);
    }

}
