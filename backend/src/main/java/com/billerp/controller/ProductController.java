package com.billerp.controller;

import com.billerp.dto.request.ProductCreateDTO;
import com.billerp.dto.request.ProductUpdateDTO;
import com.billerp.dto.response.ProductResponse;
import com.billerp.service.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.billerp.infrastructure.constants.ApiConstants.PRODUCT_PATH;
import static com.billerp.infrastructure.constants.ApiConstants.CORS_ORIGIN;
import static com.billerp.infrastructure.constants.ApiConstants.ID_PARAMETER;
import static com.billerp.infrastructure.constants.OpenApiConstants.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping(PRODUCT_PATH)
@RequiredArgsConstructor
@CrossOrigin(origins = CORS_ORIGIN)
@Tag(name = "Product", description = "Operations related to products")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create product", description = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody ProductCreateDTO dto) {
        return productService.create(dto);
    }

    @GetMapping(ID_PARAMETER)
    @Operation(summary = "Find product by ID", description = "Retrieve a single product by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse findById(@PathVariable String id) {
        return productService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Find all products", description = "Retrieve a paginated list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponse> findAll(Pageable pageable) {
        return productService.findAll(pageable);
    }

    @PutMapping
    @Operation(summary = "Update product", description = "Update an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse update(@Valid @RequestBody ProductUpdateDTO dto) {
        return productService.update(dto);
    }

    @PatchMapping(ID_PARAMETER + "/activate")
    @Operation(summary = "Activate product", description = "Set a product as active")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product activated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse activate(@PathVariable String id) {
        return productService.activate(id);
    }

    @PatchMapping(ID_PARAMETER + "/deactivate")
    @Operation(summary = "Deactivate product", description = "Set a product as inactive")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deactivated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse deactivate(@PathVariable String id) {
        return productService.deactivate(id);
    }

    @DeleteMapping(ID_PARAMETER)
    @Operation(summary = "Delete product", description = "Remove a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable String id) {
        productService.delete(id);
    }
}
