package ru.practicum.main.server.service.request;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.server.dto.request.ParticipationRequestDto;
import ru.practicum.main.server.exception.*;
import ru.practicum.main.server.model.entities.Event;
import ru.practicum.main.server.model.entities.ParticipationRequest;
import ru.practicum.main.server.model.entities.User;
import ru.practicum.main.server.model.enums.EventState;
import ru.practicum.main.server.model.enums.RequestState;
import ru.practicum.main.server.repository.event.EventRepository;
import ru.practicum.main.server.repository.request.RequestRepository;
import ru.practicum.main.server.repository.user.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final Converter<ParticipationRequest, ParticipationRequestDto> requestMapper;

    @Transactional(readOnly = true)
    @Override
    public Collection<ParticipationRequestDto> getAllUserRequests(Long requesterId) {
        userRepository.getUserById(requesterId)
                .orElseThrow(() -> new UserNotFoundException(requesterId));
        return requestRepository
                .findAllUserRequests(requesterId)
                .stream()
                .map(requestMapper::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ParticipationRequestDto createRequest(Long requesterId, Long eventId) {
        User requester = userRepository.getUserById(requesterId)
                .orElseThrow(() -> new UserNotFoundException(requesterId));
        Event event = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new EventNotFoundException(requesterId));
        if (requester.equals(event.getInitiator())) {
            throw new RequestCreationException("Requester must not be event initiator.");
        }
        if (event.getState() != EventState.PUBLISHED) {
            throw new RequestCreationException("Event must be published.");
        }
        long confirmedRequestsCount = event
                .getRequests()
                .stream()
                .filter(r -> r.getStatus() == RequestState.CONFIRMED)
                .count();
        if (event.getParticipantLimit() > 0 && confirmedRequestsCount == event.getParticipantLimit()) {
            throw new RequestCreationException("Event participant limit has been reached.");
        }
        ParticipationRequest request = new ParticipationRequest(null, event, requester);
        if (!event.getRequestModeration()) {
            request.setStatus(RequestState.CONFIRMED);
        }
        try {
            return requestMapper.convert(requestRepository.saveRequest(request));
        } catch (Exception e) {
            throw new RequestViolationException(e);
        }
    }

    @Transactional
    @Override
    public ParticipationRequestDto cancelRequest(Long requesterId, Long requestId) {
        ParticipationRequest request = requestRepository.findUserRequest(requesterId, requestId)
                .orElseThrow(() -> new RequestNotFoundException(requestId));
        request.setStatus(RequestState.REJECTED);
        return requestMapper.convert(requestRepository.saveRequest(request));
    }
}
