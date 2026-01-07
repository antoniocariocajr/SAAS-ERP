package com.billerp.controller;

import com.billerp.domain.enums.Role;
import com.billerp.dto.request.UserCreateDTO;
import com.billerp.dto.request.UserUpdateDto;
import com.billerp.dto.response.UserResponse;
import com.billerp.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.billerp.infrastructure.constants.ApiConstants.CORS_ORIGIN;
import static com.billerp.infrastructure.constants.ApiConstants.USER_PATH;
import static com.billerp.infrastructure.constants.ApiConstants.ID_PARAMETER;
import static com.billerp.infrastructure.constants.OpenApiConstants.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping(USER_PATH)
@RequiredArgsConstructor
@CrossOrigin(origins = CORS_ORIGIN)
@Tag(name = "User", description = "Operations related to users")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create user", description = "Register a new system user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "User already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public UserResponse createUser(@Valid @RequestBody UserCreateDTO dto) {
        return userService.createUser(dto);
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a paginated list of all system users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @GetMapping(ID_PARAMETER)
    @Operation(summary = "Get user by ID", description = "Retrieve a single user by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email", description = "Retrieve a single user by their email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PutMapping
    @Operation(summary = "Update user", description = "Update an existing user's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateUser(@Valid @RequestBody UserUpdateDto dto) {
        return userService.updateUser(dto);
    }

    @PatchMapping(ID_PARAMETER + "/roles/add")
    @Operation(summary = "Add role", description = "Assign a new role to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role added successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public UserResponse addRole(@PathVariable String id, @RequestParam Role role) {
        return userService.addRole(id, role);
    }

    @PatchMapping(ID_PARAMETER + "/roles/remove")
    @Operation(summary = "Remove role", description = "Revoke a role from a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role removed successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public UserResponse removeRole(@PathVariable String id, @RequestParam Role role) {
        return userService.removeRole(id, role);
    }

    @PatchMapping(ID_PARAMETER + "/enable")
    @Operation(summary = "Enable user", description = "Enable a user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User enabled successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public UserResponse enableUser(@PathVariable String id) {
        return userService.enableUser(id);
    }

    @PatchMapping(ID_PARAMETER + "/disable")
    @Operation(summary = "Disable user", description = "Disable a user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User disabled successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public UserResponse disableUser(@PathVariable String id) {
        return userService.disableUser(id);
    }

    @DeleteMapping(ID_PARAMETER)
    @Operation(summary = "Delete user", description = "Permanently remove a user from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
