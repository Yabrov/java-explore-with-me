package ru.practicum.main.server.service.event;

import ru.practicum.main.server.dto.event.EventFullDto;
import ru.practicum.main.server.dto.event.UpdateEventAdminRequest;
import ru.practicum.main.server.model.enums.EventState;

import java.time.LocalDateTime;
import java.util.Collection;

public interface EventService {

    Collection<EventFullDto> getAllEvents(Collection<Long> users,
                                          Collection<EventState> states,
                                          Collection<Long> categories,
                                          LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd,
                                          Integer from,
                                          Integer size);

    EventFullDto updateEvent(Long eventId, UpdateEventAdminRequest request);
}
