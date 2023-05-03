package ru.practicum.main.server.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class UserDto {

    private Long id;

    @JsonProperty(required = true)
    private String name;

    @JsonProperty(required = true)
    private String email;
}
