package ru.practicum.main.server.dto.compilation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class NewCompilationDto {

    private Collection<Long> events;

    @JsonProperty(defaultValue = "false")
    private Boolean pinned;

    @JsonProperty(required = true)
    private String title;
}
