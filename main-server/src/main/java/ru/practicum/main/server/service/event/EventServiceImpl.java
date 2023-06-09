package ru.practicum.main.server.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.server.dto.event.*;
import ru.practicum.main.server.dto.location.LocationDto;
import ru.practicum.main.server.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.main.server.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.main.server.dto.request.ParticipationRequestDto;
import ru.practicum.main.server.exception.*;
import ru.practicum.main.server.model.entities.*;
import ru.practicum.main.server.model.enums.*;
import ru.practicum.main.server.repository.event.EventRepository;
import ru.practicum.main.server.service.location.LocationService;
import ru.practicum.main.server.utils.PageBuilder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final LocationService locationService;
    private final Converter<Event, EventFullDto> eventMapper;
    private final Converter<Event, EventShortDto> eventToShortDtoMapper;
    private final Converter<ParticipationRequest, ParticipationRequestDto> requestMapper;
    private final Converter<NewEventDto, Event> newEventDtoMapper;
    private final Converter<LocationDto, Location> locationDtoMapper;
    private final PageBuilder pageBuilder;
    private static final String SEARCH_PATTERN = "%%%s%%";

    @Transactional(readOnly = true)
    @Override
    public Collection<EventFullDto> getAllEventsAdmin(Collection<Long> users,
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

    @Transactional(readOnly = true)
    @Override
    public Collection<EventFullDto> getAllEventsPublic(String text,
                                                       Collection<Long> categories,
                                                       Boolean paid,
                                                       LocalDateTime rangeStart,
                                                       LocalDateTime rangeEnd,
                                                       Boolean onlyAvailable,
                                                       EventSort sort,
                                                       Integer from,
                                                       Integer size) {
        Pageable pageable = pageBuilder.build(from, size, null);
        String searchPhrase = text == null ? null : String.format(SEARCH_PATTERN, text.toLowerCase());
        Collection<Event> events = eventRepository.findAllEvents(
                searchPhrase,
                categories,
                paid,
                rangeStart == null ? LocalDateTime.now() : rangeStart,
                rangeEnd,
                onlyAvailable,
                pageable);
        return sort == null || sort == EventSort.EVENT_DATE
                ? events
                .stream()
                .map(eventMapper::convert)
                .collect(Collectors.toList())
                : events
                .stream()
                .map(eventMapper::convert)
                .sorted(Comparator.comparing(EventFullDto::getViews))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public EventFullDto getPublishedEventById(Long eventId) {
        Event event = eventRepository.findPublishedEventById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        return eventMapper.convert(event);
    }

    @Transactional
    @Override
    public EventFullDto adminUpdateEvent(Long eventId, UpdateEventAdminRequest request) {
        Event event = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        if (request.getAnnotation() != null && !request.getAnnotation().isBlank()) {
            event.setAnnotation(request.getAnnotation());
        }
        if (request.getCategoryId() != null) {
            event.setCategory(new Category(request.getCategoryId(), null));
        }
        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            event.setDescription(request.getDescription());
        }
        if (request.getEventDate() != null) {
            if (request.getEventDate().isBefore(LocalDateTime.now())) {
                throw new EventUpdateException("Event date can not be in past");
            }
            if (event.getPublishedOn() != null) {
                if (Duration.between(event.getPublishedOn(), request.getEventDate()).toHours() < 1L) {
                    throw new EventUpdateException("Cannot publish the event because it has wrong eventDate");
                } else {
                    event.setEventDate(request.getEventDate());
                }
            }
        }
        if (request.getParticipantLimit() != null) {
            event.setParticipantLimit(request.getParticipantLimit());
        }
        if (request.getLocation() != null) {
            boolean checkZone = event.getParticipantLimit() > 1000;
            Location location = locationService
                    .createLocation(locationDtoMapper.convert(request.getLocation()), checkZone);
            event.setLocation(location);
        }
        if (request.getPaid() != null) {
            event.setPaid(request.getPaid());
        }
        if (request.getTitle() != null && !request.getTitle().isBlank()) {
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
                LocalDateTime estimatedPublicationDate = LocalDateTime.now();
                if (Duration.between(estimatedPublicationDate, event.getEventDate()).toHours() < 1L) {
                    throw new EventUpdateException("Cannot publish the event because it has wrong eventDate");
                }
                event.setPublishedOn(estimatedPublicationDate);
                event.setState(EventState.PUBLISHED);
            }
        }
        return eventMapper.convert(eventRepository.saveEvent(event));
    }

    @Transactional
    @Override
    public EventFullDto userUpdateEvent(Long userId, Long eventId, UpdateEventUserRequest request) {
        Event event = eventRepository.findUsersEventById(userId, eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        if (event.getState() == EventState.PUBLISHED) {
            throw new EventUpdateException("Only pending or canceled events can be changed");
        }
        if (request.getAnnotation() != null && !request.getAnnotation().isBlank()) {
            event.setAnnotation(request.getAnnotation());
        }
        if (request.getCategoryId() != null) {
            event.setCategory(new Category(request.getCategoryId(), null));
        }
        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            event.setDescription(request.getDescription());
        }
        if (request.getEventDate() != null) {
            if (Duration.between(LocalDateTime.now(), request.getEventDate()).toHours() < 2L) {
                throw new EventUpdateException("Cannot publish the event because it has wrong eventDate");
            } else {
                event.setEventDate(request.getEventDate());
            }
        }
        if (request.getParticipantLimit() != null) {
            event.setParticipantLimit(request.getParticipantLimit());
        }
        if (request.getLocation() != null) {
            boolean checkZone = event.getParticipantLimit() > 1000;
            Location location = locationService
                    .createLocation(locationDtoMapper.convert(request.getLocation()), checkZone);
            event.setLocation(location);
        }
        if (request.getPaid() != null) {
            event.setPaid(request.getPaid());
        }
        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            event.setTitle(request.getTitle());
        }
        if (request.getStateAction() != null) {
            if (request.getStateAction() == UserEventState.SEND_TO_REVIEW) {
                event.setState(EventState.PENDING);
            } else {
                event.setState(EventState.CANCELED);
            }
        }
        return eventMapper.convert(eventRepository.saveEvent(event));
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<EventShortDto> getAllUsersEvents(Long userId, Integer from, Integer size) {
        Pageable pageable = pageBuilder.build(from, size, null);
        return eventRepository
                .findAllUsersEvents(userId, pageable)
                .stream()
                .map(eventToShortDtoMapper::convert)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto eventDto) {
        Event event = newEventDtoMapper.convert(eventDto);
        if (Duration.between(event.getCreatedOn(), event.getEventDate()).toHours() < 2L) {
            throw new EventCreationException(event.getEventDate());
        }
        boolean checkZone = event.getParticipantLimit() > 1000;
        Location location = locationService.createLocation(event.getLocation(), checkZone);
        event.getLocation().setId(location.getId());
        event.setInitiator(new User(userId, null, null));
        return eventMapper.convert(eventRepository.saveEvent(event));
    }

    @Transactional(readOnly = true)
    @Override
    public EventFullDto getUsersEventById(Long userId, Long eventId) {
        Event event = eventRepository.findUsersEventById(userId, eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        return eventMapper.convert(event);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<ParticipationRequestDto> getUsersEventRequests(Long userId, Long eventId) {
        Event event = eventRepository.findUsersEventById(userId, eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        return event
                .getRequests()
                .stream()
                .map(requestMapper::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventRequestStatusUpdateResult updateUserEventRequests(EventRequestStatusUpdateRequest updateRequest,
                                                                  Long userId,
                                                                  Long eventId) {
        Event event = eventRepository.findUsersEventById(userId, eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        long confirmedRequestsCount = event
                .getRequests()
                .stream()
                .filter(r -> r.getStatus() == RequestState.CONFIRMED)
                .count();
        if (confirmedRequestsCount == event.getParticipantLimit()) {
            throw new RequestStateUpdateException("The participant limit has been reached");
        }
        Collection<ParticipationRequest> requests = event
                .getRequests()
                .stream()
                .filter(r -> updateRequest.getRequestIds().contains(r.getId()))
                .collect(Collectors.toList());
        if (event.getRequestModeration() && event.getParticipantLimit() > 0) {
            for (ParticipationRequest request : requests) {
                if (request.getStatus() != RequestState.PENDING) {
                    throw new RequestWrongStateException();
                }
                if (updateRequest.getStatus() == RequestState.CONFIRMED) {
                    if (confirmedRequestsCount < event.getParticipantLimit()) {
                        request.setStatus(RequestState.CONFIRMED);
                        confirmedRequestsCount++;
                    } else {
                        request.setStatus(RequestState.REJECTED);
                    }
                } else {
                    request.setStatus(RequestState.REJECTED);
                }
            }
        }
        Collection<ParticipationRequestDto> confirmedRequests = requests
                .stream()
                .filter(r -> r.getStatus() == RequestState.CONFIRMED)
                .map(requestMapper::convert)
                .collect(Collectors.toList());
        Collection<ParticipationRequestDto> rejectedRequests = requests
                .stream()
                .filter(r -> r.getStatus() == RequestState.REJECTED)
                .map(requestMapper::convert)
                .collect(Collectors.toList());
        return new EventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
    }

    @Override
    public Collection<EventFullDto> findAllEventsInsideZone(float longitude, float latitude, float radius) {
        return eventRepository
                .findAllEventsInsideZone(longitude, latitude, radius)
                .stream()
                .map(eventMapper::convert)
                .collect(Collectors.toList());
    }
}
