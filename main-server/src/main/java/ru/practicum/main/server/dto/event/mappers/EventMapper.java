package ru.practicum.main.server.dto.event.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.dto.category.CategoryDto;
import ru.practicum.main.server.dto.event.EventFullDto;
import ru.practicum.main.server.dto.location.LocationDto;
import ru.practicum.main.server.dto.user.UserShortDto;
import ru.practicum.main.server.model.entities.Category;
import ru.practicum.main.server.model.entities.Event;
import ru.practicum.main.server.model.entities.Location;
import ru.practicum.main.server.model.entities.User;
import ru.practicum.main.server.model.enums.RequestState;
import ru.practicum.stats.client.service.StatsServerClient;
import ru.practicum.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventMapper implements Converter<Event, EventFullDto> {

    private final StatsServerClient statsServerClient;
    private final Converter<Category, CategoryDto> categoryMapper;
    private final Converter<Location, LocationDto> locationMapper;
    private final Converter<User, UserShortDto> userMapper;

    @Override
    public EventFullDto convert(Event source) {
        Collection<ViewStatsDto> views = statsServerClient.getStats(
                source.getPublishedOn(),
                LocalDateTime.now(),
                List.of("/event/" + source.getId()),
                false);
        long confirmedRequestsCount = source
                .getRequests()
                .stream()
                .filter(r -> r.getStatus() == RequestState.CONFIRMED)
                .count();
        return new EventFullDto(
                source.getId(),
                source.getAnnotation(),
                categoryMapper.convert(source.getCategory()),
                confirmedRequestsCount,
                source.getCreatedOn(),
                source.getDescription(),
                source.getEventDate(),
                userMapper.convert(source.getInitiator()),
                locationMapper.convert(source.getLocation()),
                source.getPaid(),
                source.getParticipantLimit(),
                source.getPublishedOn(),
                source.getRequestModeration(),
                source.getState(),
                source.getTitle(),
                (long) views.size()
        );
    }
}
