package ru.practicum.main.server.service.category;

import ru.practicum.main.server.dto.category.CategoryDto;
import ru.practicum.main.server.dto.category.NewCategoryDto;

import java.util.Collection;

public interface CategoryService {

    CategoryDto getCategory(Long categoryId);

    Collection<CategoryDto> getAllCategories(Integer from, Integer size);

    CategoryDto createCategory(NewCategoryDto categoryDto);

    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);

    void deleteCategory(Long categoryId);
}
