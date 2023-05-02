package ru.practicum.main.server.repository.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.main.server.model.entities.Event;
import ru.practicum.main.server.model.enums.EventState;
import ru.practicum.main.server.model.enums.RequestState;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface EventJpaRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e " +
            "WHERE (COALESCE(:users, null) IS null OR e.initiator.id IN :users) " +
            "AND (COALESCE(:states, null) IS null OR e.state IN :states) " +
            "AND (COALESCE(:categories, null) IS null OR e.category.id IN :categories) " +
            "AND (COALESCE(:rangeStart, null) IS null OR e.eventDate >= :rangeStart) " +
            "AND (COALESCE(:rangeEnd, null) IS null OR e.eventDate <= :rangeEnd)")
    Page<Event> getEvents(Collection<Long> users,
                          Collection<EventState> states,
                          Collection<Long> categories,
                          LocalDateTime rangeStart,
                          LocalDateTime rangeEnd,
                          Pageable pageable);

    @Query("SELECT e FROM Event e LEFT JOIN ParticipationRequest r ON r.event.id = e.id AND r.status = :reqState GROUP BY e.id " +
            "HAVING (COALESCE(:text, null) IS null OR LOWER(e.annotation || e.description) LIKE :text) " +
            "AND (COALESCE(:categories, null) IS null OR e.category.id IN :categories) " +
            "AND (:rangeStart <= e.eventDate) " +
            "AND (COALESCE(:rangeEnd, null) IS null OR e.eventDate <= :rangeEnd) " +
            "AND (:onlyAvailable = false OR e.participantLimit > COUNT(r))" +
            "AND (COALESCE(:paid, null) IS null OR e.paid = :paid) " +
            "AND e.state = :state " +
            "ORDER BY e.eventDate")
    Page<Event> getEvents(String text,
                          Collection<Long> categories,
                          EventState state,
                          RequestState reqState,
                          Boolean paid,
                          LocalDateTime rangeStart,
                          LocalDateTime rangeEnd,
                          Boolean onlyAvailable,
                          Pageable pageable);

    Optional<Event> findEventByIdAndState(Long eventId, EventState state);

    Page<Event> findAllByInitiator_Id(Long initiatorId, Pageable pageable);

    Optional<Event> findEventByIdAndInitiator_Id(Long eventId, Long initiatorId);

    Collection<Event> findAllByIdIn(Collection<Long> ids);
}
