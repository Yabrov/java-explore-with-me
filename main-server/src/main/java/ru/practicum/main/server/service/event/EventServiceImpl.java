package ru.practicum.main.server.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main.server.dto.event.EventFullDto;
import ru.practicum.main.server.dto.event.UpdateEventAdminRequest;
import ru.practicum.main.server.dto.location.LocationDto;
import ru.practicum.main.server.exception.EventNotFoundException;
import ru.practicum.main.server.exception.EventUpdateException;
import ru.practicum.main.server.model.entities.Category;
import ru.practicum.main.server.model.entities.Event;
import ru.practicum.main.server.model.entities.Location;
import ru.practicum.main.server.model.enums.AdminEventState;
import ru.practicum.main.server.model.enums.EventState;
import ru.practicum.main.server.repository.event.EventRepository;
import ru.practicum.main.server.service.location.LocationService;
import ru.practicum.main.server.utils.PageBuilder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final LocationService locationService;
    private final Converter<Event, EventFullDto> eventMapper;
    private final Converter<LocationDto, Location> locationDtoMapper;
    private final PageBuilder pageBuilder;

    @Override
    public Collection<EventFullDto> getAllEvents(Collection<Long> users,
                                                 Collection<EventState> states,
                                                 Collection<Long> categories,
                                                 LocalDateTime rangeStart,
                                                 LocalDateTime rangeEnd,
                                                 Integer from,
                                                 Integer size) {
        Pageable pageable = pageBuilder.build(from, size, null);
        return eventRepository
                .findAllEvents(users, states, categories, rangeStart, rangeEnd, pageable)
                .stream()
                .map(eventMapper::convert)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEvent(Long eventId, UpdateEventAdminRequest request) {
        Event event = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        if (request.getAnnotation() != null) {
            event.setAnnotation(request.getAnnotation());
        }
        if (request.getCategoryId() != null) {
            event.setCategory(new Category(request.getCategoryId(), null));
        }
        if (request.getDescription() != null) {
            event.setDescription(request.getDescription());
        }
        if (request.getEventDate() != null) {
            if (Duration.between(event.getPublishedOn(), request.getEventDate()).toHours() < 1L) {
                throw new EventUpdateException("Cannot publish the event because it has wrong eventDate");
            } else {
                event.setEventDate(request.getEventDate());
            }
        }
        if (request.getLocation() != null) {
            Location location = locationService
                    .createLocation(locationDtoMapper.convert(request.getLocation()));
            event.setLocation(location);
        }
        if (request.getPaid() != null) {
            event.setPaid(request.getPaid());
        }
        if (request.getParticipantLimit() != null) {
            event.setParticipantLimit(request.getParticipantLimit());
        }
        if (request.getTitle() != null) {
            event.setTitle(request.getTitle());
        }
        if (request.getStateAction() != null) {
            if (request.getStateAction() == AdminEventState.REJECT_EVENT) {
                if (event.getState() == EventState.PUBLISHED) {
                    throw new EventUpdateException("Cannot reject the event because it's not in the right state: PUBLISHED");
                }
                event.setState(EventState.CANCELED);
            } else {
                if (event.getState() != EventState.PENDING) {
                    throw new EventUpdateException("Cannot publish the event because it's not in the right state: PENDING");
                }
                event.setState(EventState.PUBLISHED);
            }
        }
        return eventMapper.convert(eventRepository.saveEvent(event));
    }
}
