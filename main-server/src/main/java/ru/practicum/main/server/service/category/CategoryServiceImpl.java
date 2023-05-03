package ru.practicum.main.server.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.server.dto.category.CategoryDto;
import ru.practicum.main.server.dto.category.NewCategoryDto;
import ru.practicum.main.server.exception.CategoryIsNotEmptyException;
import ru.practicum.main.server.exception.CategoryNotFoundException;
import ru.practicum.main.server.exception.ConstraintViolationException;
import ru.practicum.main.server.model.entities.Category;
import ru.practicum.main.server.repository.category.CategoryRepository;
import ru.practicum.main.server.utils.PageBuilder;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final Converter<NewCategoryDto, Category> newCategoryDtoMapper;
    private final Converter<CategoryDto, Category> categoryDtoMapper;
    private final Converter<Category, CategoryDto> categoryMapper;
    private final PageBuilder pageBuilder;

    @Transactional(readOnly = true)
    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepository.findCategoryById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        return categoryMapper.convert(category);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<CategoryDto> getAllCategories(Integer from, Integer size) {
        Pageable pageable = pageBuilder.build(from, size, null);
        return categoryRepository
                .findAllCategories(pageable)
                .stream()
                .map(categoryMapper::convert)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto createCategory(NewCategoryDto categoryDto) {
        try {
            Category category = newCategoryDtoMapper.convert(categoryDto);
            return categoryMapper.convert(categoryRepository.saveCategory(category));
        } catch (Exception e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        try {
            Category category = categoryDtoMapper.convert(categoryDto);
            return categoryMapper.convert(categoryRepository.saveCategory(category));
        } catch (Exception e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findCategoryById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        if (!category.getEvents().isEmpty()) {
            throw new CategoryIsNotEmptyException();
        }
        categoryRepository.deleteCategory(categoryId);
    }
}
