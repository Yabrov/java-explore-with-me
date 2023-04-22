package ru.practicum.main.server.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserShortDto {

    @JsonProperty(required = true)
    private Long id;

    @JsonProperty(required = true)
    private String name;
}
