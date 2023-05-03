package ru.practicum.main.server.service.request;

import ru.practicum.main.server.dto.request.ParticipationRequestDto;

import java.util.Collection;

public interface RequestService {

    Collection<ParticipationRequestDto> getAllUserRequests(Long requesterId);

    ParticipationRequestDto createRequest(Long requesterId, Long eventId);

    ParticipationRequestDto cancelRequest(Long requesterId, Long requestId);
}
