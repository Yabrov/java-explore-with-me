package ru.practicum.main.server.repository.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.server.model.entities.Event;
import ru.practicum.main.server.model.enums.EventState;

import java.time.LocalDateTime;
import java.util.Collection;

public interface EventJpaRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e " +
            "WHERE (COALESCE(:users, null) IS null OR e.initiator.id IN :users) " +
            "AND (COALESCE(:states, null) IS null OR e.state IN :states) " +
            "AND (COALESCE(:categories, null) IS null OR e.category.id IN :categories) " +
            "AND (COALESCE(:rangeStart, null) IS null OR e.eventDate >= :rangeStart) " +
            "AND (COALESCE(:rangeEnd, null) IS null OR e.eventDate <= :rangeEnd)")
    Collection<Event> getEvents(Collection<Long> users,
                                Collection<EventState> states,
                                Collection<Long> categories,
                                LocalDateTime rangeStart,
                                LocalDateTime rangeEnd,
                                Pageable pageable);
}
