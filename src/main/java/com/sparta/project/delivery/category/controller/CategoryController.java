package com.sparta.project.delivery.category.controller;

import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.category.dto.CategoryRequest;
import com.sparta.project.delivery.category.dto.CategoryResponse;
import com.sparta.project.delivery.category.service.CategoryService;
import com.sparta.project.delivery.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/category")
@Tag(name = "Category API", description = "카테고리를 추가 수정 조회 할 수 있는 API 입니다.")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "category 조회 API", description = "Category 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    public Page<CategoryResponse> getCategories(
            @ParameterObject
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return categoryService.getCategories(pageable).map(CategoryResponse::from);
    }

    @PostMapping
    @Operation(summary = "category 생성 API", description = "Category 를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 자격 증명입니다.", content = @Content),
            @ApiResponse(responseCode = "802", description = "이미 존재하는 카테고리입니다.", content = @Content),
            @ApiResponse(responseCode = "803", description = "카테고리 생성하는데 필수값이 없습니다.", content = @Content)
    })
    public String addCategory(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CategoryRequest request
    ) {
        categoryService.addCategory(UserDto.from(userDetails.getUser()), request.toDto());

        return "category  create successfully";
    }

    @DeleteMapping("/{categoryId}/delete")
    @Operation(summary = "category 삭제 API", description = "Category 정보를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 자격 증명입니다.", content = @Content),
            @ApiResponse(responseCode = "801", description = "카테고리를 찾을 수 없습니다.", content = @Content),
    })
    public String deleteCategory(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("categoryId") String categoryId
    ) {
        categoryService.deleteCategory(UserDto.from(userDetails.getUser()), categoryId);

        return "category delete successfully";
    }

    @PutMapping("/{categoryId}/update")
    @Operation(summary = "category 수정 API", description = "기존에 있던 Category 를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 자격 증명입니다.", content = @Content),
            @ApiResponse(responseCode = "801", description = "카테고리를 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "802", description = "이미 존재하는 카테고리입니다.", content = @Content),
            @ApiResponse(responseCode = "803", description = "카테고리 생성하는데 필수값이 없습니다.", content = @Content)
    })
    public void updateCategory(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("categoryId") String categoryId,
            @RequestBody CategoryRequest request
    ) {
        categoryService.updateCategory(UserDto.from(userDetails.getUser()), categoryId, request.toDto());
    }
}
