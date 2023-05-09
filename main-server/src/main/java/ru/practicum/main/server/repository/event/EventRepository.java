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

    Collection<Event> findAllEvents(String text,
                                    Collection<Long> categories,
                                    Boolean paid,
                                    LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd,
                                    Boolean onlyAvailable,
                                    Pageable pageable);

    Optional<Event> findEventById(Long eventId);

    Optional<Event> findPublishedEventById(Long eventId);

    Event saveEvent(Event event);

    Collection<Event> findAllUsersEvents(Long userId, Pageable pageable);

    Optional<Event> findUsersEventById(Long userId, Long eventId);

    Collection<Event> findAllEventsByIds(Collection<Long> ids);

    Collection<Event> findAllEventsInsideZone(float longitude, float latitude, float radius);
}
