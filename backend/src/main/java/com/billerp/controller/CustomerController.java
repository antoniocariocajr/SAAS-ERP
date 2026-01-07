package com.billerp.controller;

import com.billerp.dto.request.CustomerCreateDTO;
import com.billerp.dto.request.CustomerUpdateDTO;
import com.billerp.dto.response.CustomerResponse;
import com.billerp.service.interfaces.CustomerService;
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

import static com.billerp.infrastructure.constants.ApiConstants.CUSTOMER_PATH;
import static com.billerp.infrastructure.constants.ApiConstants.ID_PARAMETER;
import static com.billerp.infrastructure.constants.OpenApiConstants.SECURITY_SCHEME_NAME;
import static com.billerp.infrastructure.constants.ApiConstants.CORS_ORIGIN;

@RestController
@RequestMapping(CUSTOMER_PATH)
@RequiredArgsConstructor
@CrossOrigin(origins = CORS_ORIGIN)
@Tag(name = "Customer", description = "Operations related to customers")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @Operation(summary = "Create customer", description = "Create a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(@Valid @RequestBody CustomerCreateDTO dto) {
        return customerService.create(dto);
    }

    @GetMapping(ID_PARAMETER)
    @Operation(summary = "Find customer by ID", description = "Retrieve a single customer by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse findById(@PathVariable String id) {
        return customerService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Find all customers", description = "Retrieve a paginated list of all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers found successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    public Page<CustomerResponse> findAll(Pageable pageable) {
        return customerService.findAll(pageable);
    }

    @PutMapping
    @Operation(summary = "Update customer", description = "Update an existing customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse update(@Valid @RequestBody CustomerUpdateDTO dto) {
        return customerService.update(dto);
    }

    @PatchMapping(ID_PARAMETER + "/activate")
    @Operation(summary = "Activate customer", description = "Set a customer as active")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer activated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public CustomerResponse activate(@PathVariable String id) {
        return customerService.activate(id);
    }

    @PatchMapping(ID_PARAMETER + "/deactivate")
    @Operation(summary = "Deactivate customer", description = "Set a customer as inactive")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer deactivated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public CustomerResponse deactivate(@PathVariable String id) {
        return customerService.deactivate(id);
    }

    @DeleteMapping(ID_PARAMETER)
    @Operation(summary = "Delete customer", description = "Remove a customer by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable String id) {
        customerService.delete(id);
    }
}
