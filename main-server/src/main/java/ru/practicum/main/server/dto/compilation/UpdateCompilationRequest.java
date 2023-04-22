package ru.practicum.main.server.dto.compilation;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class UpdateCompilationRequest {
    private Collection<Long> events;
    private Boolean pinned;
    private String title;
}
