package ru.practicum.main.server.service.event;

import ru.practicum.main.server.dto.event.*;
import ru.practicum.main.server.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.main.server.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.main.server.dto.request.ParticipationRequestDto;
import ru.practicum.main.server.model.enums.EventSort;
import ru.practicum.main.server.model.enums.EventState;

import java.time.LocalDateTime;
import java.util.Collection;

public interface EventService {

    Collection<EventFullDto> getAllEventsAdmin(Collection<Long> users,
                                               Collection<EventState> states,
                                               Collection<Long> categories,
                                               LocalDateTime rangeStart,
                                               LocalDateTime rangeEnd,
                                               Integer from,
                                               Integer size);

    Collection<EventFullDto> getAllEventsPublic(String text,
                                                Collection<Long> categories,
                                                Boolean paid,
                                                LocalDateTime rangeStart,
                                                LocalDateTime rangeEnd,
                                                Boolean onlyAvailable,
                                                EventSort sort,
                                                Integer from,
                                                Integer size);

    EventFullDto adminUpdateEvent(Long eventId, UpdateEventAdminRequest request);

    EventFullDto userUpdateEvent(Long userId, Long eventId, UpdateEventUserRequest request);

    EventFullDto getPublishedEventById(Long eventId);

    Collection<EventShortDto> getAllUsersEvents(Long userId, Integer from, Integer size);

    EventFullDto createEvent(Long userId, NewEventDto eventDto);

    EventFullDto getUsersEventById(Long userId, Long eventId);

    Collection<ParticipationRequestDto> getUsersEventRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateUserEventRequests(EventRequestStatusUpdateRequest updateRequest,
                                                           Long userId,
                                                           Long eventId);

    Collection<EventFullDto> findAllEventsInsideZone(float longitude, float latitude, float radius);
}
