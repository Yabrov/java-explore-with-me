package ru.practicum.main.server.dto.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class EventRequestStatusUpdateRequest {
    private String description;
    private Collection<Long> requestIds;
    private String status;
}
