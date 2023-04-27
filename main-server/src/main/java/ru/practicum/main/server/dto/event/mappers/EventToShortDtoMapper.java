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
import ru.practicum.main.server.model.enums.EventState;
import ru.practicum.main.server.model.enums.RequestState;
import ru.practicum.stats.client.service.StatsServerClient;
import ru.practicum.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventToShortDtoMapper implements Converter<Event, EventShortDto> {

    private final StatsServerClient statsServerClient;
    private final Converter<Category, CategoryDto> categoryMapper;
    private final Converter<User, UserShortDto> userMapper;

    @Override
    public EventShortDto convert(Event source) {
        Collection<ViewStatsDto> views = source.getState() == EventState.PUBLISHED
                ? statsServerClient.getStats(
                source.getPublishedOn(),
                LocalDateTime.now(),
                List.of("/event/" + source.getId()),
                false)
                : Collections.emptyList();
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
                (long) views.size()
        );
    }
}
