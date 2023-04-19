package ru.practicum.main.server.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {

    private Long id;

    @JsonProperty(required = true)
    private String name;

    @JsonProperty(required = true)
    private String email;
}
