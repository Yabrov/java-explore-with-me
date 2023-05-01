package ru.practicum.main.server.dto.event.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.dto.category.CategoryDto;
import ru.practicum.main.server.dto.event.EventShortDto;
import ru.practicum.main.server.dto.user.UserShortDto;
import ru.practicum.main.server.model.entities.Category;
import ru.practicum.main.server.model.entities.Event;
import ru.practicum.main.server.model.entities.User;
import ru.practicum.main.server.model.enums.RequestState;

@Component
@RequiredArgsConstructor
public class EventToShortDtoMapper implements Converter<Event, EventShortDto> {

    private final Converter<Category, CategoryDto> categoryMapper;
    private final Converter<User, UserShortDto> userMapper;

    @Override
    public EventShortDto convert(Event source) {
        long confirmedRequestsCount = source
                .getRequests()
                .stream()
                .filter(r -> r.getStatus() == RequestState.CONFIRMED)
                .count();
        return new EventShortDto(
                source.getId(),
                source.getAnnotation(),
                categoryMapper.convert(source.getCategory()),
                confirmedRequestsCount,
                source.getEventDate(),
                userMapper.convert(source.getInitiator()),
                source.getPaid(),
                source.getTitle(),
                source.getViews()
        );
    }
}
