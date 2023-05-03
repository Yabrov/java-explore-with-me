package ru.practicum.main.server.repository.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.main.server.model.entities.ParticipationRequest;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestRepositoryImpl implements RequestRepository {

    private final RequestJpaRepository requestJpaRepository;

    @Override
    public Collection<ParticipationRequest> findAllUserRequests(Long requesterId) {
        return requestJpaRepository.findAllByRequester_Id(requesterId);
    }

    @Override
    public ParticipationRequest saveRequest(ParticipationRequest request) {
        return requestJpaRepository.save(request);
    }

    @Override
    public Optional<ParticipationRequest> findUserRequest(Long requesterId, Long requestId) {
        return requestJpaRepository.findByIdAndRequester_Id(requestId, requesterId);
    }
}
