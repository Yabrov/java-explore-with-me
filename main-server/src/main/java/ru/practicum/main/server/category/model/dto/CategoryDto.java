package ru.practicum.main.server.category.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryDto {

    private Long id;

    @JsonProperty(required = true)
    private String name;
}