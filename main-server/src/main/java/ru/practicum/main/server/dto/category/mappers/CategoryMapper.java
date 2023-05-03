package ru.practicum.main.server.dto.category.mappers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.dto.category.CategoryDto;
import ru.practicum.main.server.model.entities.Category;

@Component
public class CategoryMapper implements Converter<Category, CategoryDto> {

    @Override
    public CategoryDto convert(Category source) {
        return new CategoryDto(source.getId(), source.getName());
    }
}
