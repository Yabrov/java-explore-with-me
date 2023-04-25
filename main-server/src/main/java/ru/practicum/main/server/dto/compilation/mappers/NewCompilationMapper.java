package ru.practicum.main.server.dto.compilation.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.server.dto.compilation.NewCompilationDto;
import ru.practicum.main.server.model.entities.Compilation;
import ru.practicum.main.server.repository.event.EventRepository;

@Component
@RequiredArgsConstructor
public class NewCompilationMapper implements Converter<NewCompilationDto, Compilation> {

    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    @Override
    public Compilation convert(NewCompilationDto source) {
        return new Compilation(
                null,
                source.getPinned(),
                source.getTitle(),
                eventRepository.findAllEventsByIds(source.getEvents())
        );
    }
}
