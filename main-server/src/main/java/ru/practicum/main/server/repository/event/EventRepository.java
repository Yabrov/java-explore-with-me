package ru.practicum.main.server.repository.event;

import org.springframework.data.domain.Pageable;
import ru.practicum.main.server.model.entities.Event;
import ru.practicum.main.server.model.enums.EventState;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface EventRepository {

    Collection<Event> findAllEvents(Collection<Long> users,
                                    Collection<EventState> states,
                                    Collection<Long> categories,
                                    LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd,
                                    Pageable pageable);

    Optional<Event> findEventById(Long eventId);

    Event saveEvent(Event event);
}
