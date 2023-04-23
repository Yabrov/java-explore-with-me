package ru.practicum.main.server.dto.event.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.dto.event.NewEventDto;
import ru.practicum.main.server.dto.location.LocationDto;
import ru.practicum.main.server.model.entities.Category;
import ru.practicum.main.server.model.entities.Event;
import ru.practicum.main.server.model.entities.Location;

@Component
@RequiredArgsConstructor
public class NewEventDtoMapper implements Converter<NewEventDto, Event> {

    private final Converter<LocationDto, Location> locationDtoMapper;

    @Override
    public Event convert(NewEventDto source) {
        return new Event(
                null,
                source.getAnnotation(),
                new Category(source.getCategoryId(), null),
                source.getDescription(),
                source.getEventDate(),
                null,
                locationDtoMapper.convert(source.getLocation()),
                source.getPaid(),
                source.getParticipantLimit(),
                source.getRequestModeration(),
                source.getTitle()
        );
    }
}
