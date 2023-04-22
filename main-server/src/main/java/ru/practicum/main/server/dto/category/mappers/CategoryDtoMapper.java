package ru.practicum.main.server.dto.category.mappers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.dto.category.CategoryDto;
import ru.practicum.main.server.model.entities.Category;

@Component
public class CategoryDtoMapper implements Converter<CategoryDto, Category> {

    @Override
    public Category convert(CategoryDto source) {
        return new Category(source.getId(), source.getName());
    }
}
