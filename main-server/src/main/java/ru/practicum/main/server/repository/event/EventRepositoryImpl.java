package ru.practicum.main.server.repository.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.main.server.model.entities.Event;
import ru.practicum.main.server.model.enums.EventState;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository jpaRepository;

    @Override
    public Collection<Event> findAllEvents(Collection<Long> users,
                                           Collection<EventState> states,
                                           Collection<Long> categories,
                                           LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd,
                                           Pageable pageable) {
        return jpaRepository.getEvents(users, states, categories, rangeStart, rangeEnd, pageable);
    }

    @Override
    public Optional<Event> findEventById(Long eventId) {
        return jpaRepository.findById(eventId);
    }

    @Override
    public Event saveEvent(Event event) {
        return jpaRepository.save(event);
    }
}
