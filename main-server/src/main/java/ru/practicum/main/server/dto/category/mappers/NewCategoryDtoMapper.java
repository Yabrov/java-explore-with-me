package ru.practicum.main.server.dto.category.mappers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.dto.category.NewCategoryDto;
import ru.practicum.main.server.model.entities.Category;

@Component
public class NewCategoryDtoMapper implements Converter<NewCategoryDto, Category> {

    @Override
    public Category convert(NewCategoryDto source) {
        return new Category(null, source.getName());
    }
}
