package com.billerp.controller;

import com.billerp.dto.request.CategoryCreateDTO;
import com.billerp.dto.request.CategoryUpdateDTO;
import com.billerp.dto.response.CategoryResponse;
import com.billerp.service.interfaces.CategoryService;
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
import org.springframework.web.bind.annotation.*;

import static com.billerp.infrastructure.constants.ApiConstants.CATEGORY_PATH;
import static com.billerp.infrastructure.constants.ApiConstants.CORS_ORIGIN;
import static com.billerp.infrastructure.constants.ApiConstants.ID_PARAMETER;
import static com.billerp.infrastructure.constants.OpenApiConstants.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping(CATEGORY_PATH)
@RequiredArgsConstructor
@CrossOrigin(origins = CORS_ORIGIN)
@Tag(name = "Category", description = "Operations related to product categories")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Create category", description = "Create a new product category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@Valid @RequestBody CategoryCreateDTO dto) {
        return categoryService.create(dto);
    }

    @GetMapping
    @Operation(summary = "Find all categories", description = "Retrieve a paginated list of all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories found successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    public Page<CategoryResponse> findAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping(ID_PARAMETER)
    @Operation(summary = "Find category by ID", description = "Retrieve a single category by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponse findById(@PathVariable String id) {
        return categoryService.findById(id);
    }

    @PutMapping
    @Operation(summary = "Update category", description = "Update an existing category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponse update(@Valid @RequestBody CategoryUpdateDTO dto) {
        return categoryService.update(dto);
    }

    @DeleteMapping(ID_PARAMETER)
    @Operation(summary = "Delete category", description = "Remove a category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        categoryService.deleteById(id);
    }
}
