package ru.practicum.main.server.repository.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main.server.model.entities.Event;
import ru.practicum.main.server.model.enums.EventState;
import ru.practicum.main.server.model.enums.RequestState;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository eventJpaRepository;

    @Override
    public Collection<Event> findAllEvents(Collection<Long> users,
                                           Collection<EventState> states,
                                           Collection<Long> categories,
                                           LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd,
                                           Pageable pageable) {
        return eventJpaRepository.getEvents(users, states, categories, rangeStart, rangeEnd, pageable).getContent();
    }

    @Override
    public Collection<Event> findAllEvents(String text,
                                           Collection<Long> categories,
                                           Boolean paid,
                                           LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd,
                                           Boolean onlyAvailable,
                                           Pageable pageable) {
        return eventJpaRepository.getEvents(
                text,
                categories,
                EventState.PUBLISHED,
                RequestState.CONFIRMED,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                pageable).getContent();
    }

    @Override
    public Optional<Event> findEventById(Long eventId) {
        return eventJpaRepository.findById(eventId);
    }

    @Override
    public Optional<Event> findPublishedEventById(Long eventId) {
        return eventJpaRepository.findEventByIdAndState(eventId, EventState.PUBLISHED);
    }

    @Override
    public Event saveEvent(Event event) {
        return eventJpaRepository.save(event);
    }

    @Override
    public Collection<Event> findAllUsersEvents(Long userId, Pageable pageable) {
        return eventJpaRepository.findAllByInitiator_Id(userId, pageable).getContent();
    }

    @Override
    public Optional<Event> findUsersEventById(Long userId, Long eventId) {
        return eventJpaRepository.findEventByIdAndInitiator_Id(eventId, userId);
    }

    @Override
    public Collection<Event> findAllEventsByIds(Collection<Long> ids) {
        return eventJpaRepository.findAllByIdIn(ids);
    }
}
