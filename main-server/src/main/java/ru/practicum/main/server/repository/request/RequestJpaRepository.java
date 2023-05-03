package ru.practicum.main.server.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.server.model.entities.ParticipationRequest;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface RequestJpaRepository extends JpaRepository<ParticipationRequest, Long> {

    Collection<ParticipationRequest> findAllByRequester_Id(Long requesterId);

    Optional<ParticipationRequest> findByIdAndRequester_Id(Long requestId, Long requesterId);
}
