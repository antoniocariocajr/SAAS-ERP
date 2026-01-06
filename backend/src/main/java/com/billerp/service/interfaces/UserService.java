package com.billerp.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.billerp.domain.enums.Role;
import com.billerp.dto.request.UserCreateDTO;
import com.billerp.dto.request.UserUpdateDto;
import com.billerp.dto.response.UserResponse;

public interface UserService {

    UserResponse createUser(UserCreateDTO dto);

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getUserById(String id);

    UserResponse getUserByEmail(String email);

    UserResponse updateUser(UserUpdateDto dto);

    UserResponse addRole(String id, Role role);

    UserResponse removeRole(String id, Role role);

    UserResponse enableUser(String id);

    UserResponse disableUser(String id);

    void deleteUser(String id);
}
