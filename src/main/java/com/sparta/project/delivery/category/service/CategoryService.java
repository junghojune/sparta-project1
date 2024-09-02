package com.sparta.project.delivery.category.service;

import com.sparta.project.delivery.category.dto.CategoryDto;
import com.sparta.project.delivery.category.entity.Category;
import com.sparta.project.delivery.category.repository.CategoryRepository;
import com.sparta.project.delivery.common.exception.CustomException;
import com.sparta.project.delivery.user.User;
import com.sparta.project.delivery.user.dto.UserDto;
import com.sparta.project.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.project.delivery.common.exception.DeliveryError.*;
import static com.sparta.project.delivery.common.type.UserRoleEnum.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public Page<CategoryDto> getCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(CategoryDto::from);
    }

    @Transactional
    public void addCategory(UserDto userDto, CategoryDto dto) {
        User user = userRepository.findById(userDto.userId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (user.getRole().equals(OWNER) || user.getRole().equals(CUSTOMER)) {
            throw new CustomException(AUTH_INVALID_CREDENTIALS);
        }

        if (dto.name() == null || dto.name().isEmpty()) {
            throw new CustomException(CATEGORY_ARGS_EMPTY);
        }

        if (categoryRepository.findByName(dto.name()).isPresent()) {
            throw new CustomException(CATEGORY_ALREADY_EXISTS);
        }

        categoryRepository.save(dto.toEntity());
    }

    @Transactional
    public void deleteCategory(UserDto userDto, String categoryId) {
        User user = userRepository.findById(userDto.userId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (user.getRole().equals(MANAGER) || user.getRole().equals(MASTER)) {
            throw new CustomException(AUTH_INVALID_CREDENTIALS);
        }

        if (categoryRepository.findById(categoryId).isEmpty()) {
            throw new CustomException(CATEGORY_NOT_FOUND);
        }

        categoryRepository.deleteById(categoryId);
    }

    @Transactional
    public void updateCategory(UserDto userDto, String categoryId, CategoryDto dto) {
        User user = userRepository.findById(userDto.userId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (user.getRole().equals(MANAGER) || user.getRole().equals(MASTER)) {
            throw new CustomException(AUTH_INVALID_CREDENTIALS);
        }

        if (dto.name() == null || dto.name().isEmpty()) {
            throw new CustomException(CATEGORY_ARGS_EMPTY);
        }

        if (categoryRepository.findByName(dto.name()).isPresent()) {
            throw new CustomException(CATEGORY_ALREADY_EXISTS);
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(CATEGORY_NOT_FOUND));

        category.setName(dto.name());
    }
}
