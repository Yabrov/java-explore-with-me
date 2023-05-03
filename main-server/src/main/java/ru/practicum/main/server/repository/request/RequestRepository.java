package ru.practicum.main.server.repository.request;

import ru.practicum.main.server.model.entities.ParticipationRequest;

import java.util.Collection;
import java.util.Optional;

public interface RequestRepository {

    Collection<ParticipationRequest> findAllUserRequests(Long requesterId);

    ParticipationRequest saveRequest(ParticipationRequest request);

    Optional<ParticipationRequest> findUserRequest(Long requesterId, Long requestId);
}
