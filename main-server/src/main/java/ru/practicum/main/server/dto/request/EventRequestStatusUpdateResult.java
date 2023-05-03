package ru.practicum.main.server.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class EventRequestStatusUpdateResult {
    private Collection<ParticipationRequestDto> confirmedRequests;
    private Collection<ParticipationRequestDto> rejectedRequests;
}
