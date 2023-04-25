package ru.practicum.main.server.dto.compilation.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.dto.compilation.CompilationDto;
import ru.practicum.main.server.dto.event.EventShortDto;
import ru.practicum.main.server.model.entities.Compilation;
import ru.practicum.main.server.model.entities.Event;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper implements Converter<Compilation, CompilationDto> {

    private final Converter<Event, EventShortDto> eventToShortDtoMapper;

    @Override
    public CompilationDto convert(Compilation source) {
        Collection<EventShortDto> events = source
                .getEvents()
                .stream()
                .map(eventToShortDtoMapper::convert)
                .collect(Collectors.toList());
        return new CompilationDto(
                source.getId(),
                events,
                source.getPinned(),
                source.getTitle()
        );
    }
}
