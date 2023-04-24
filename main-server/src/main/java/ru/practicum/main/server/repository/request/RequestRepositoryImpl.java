package ru.practicum.main.server.repository.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.main.server.model.entities.ParticipationRequest;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RequestRepositoryImpl implements RequestRepository {

    private final RequestJpaRepository jpaRepository;

    @Override
    public Collection<ParticipationRequest> findAllUserRequests(Long requesterId) {
        return jpaRepository.findAllByRequester_Id(requesterId);
    }

    @Override
    public ParticipationRequest saveRequest(ParticipationRequest request) {
        return jpaRepository.save(request);
    }

    @Override
    public Optional<ParticipationRequest> findUserRequest(Long requesterId, Long requestId) {
        return jpaRepository.findByIdAndRequester_Id(requestId, requesterId);
    }
}
