package ru.practicum.main.server.dto.compilation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.main.server.dto.event.EventShortDto;

import java.util.Collection;

@Getter
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class CompilationDto {

    @JsonProperty(required = true)
    private Long id;

    private Collection<EventShortDto> events;

    private Boolean pinned;

    private String title;
}
