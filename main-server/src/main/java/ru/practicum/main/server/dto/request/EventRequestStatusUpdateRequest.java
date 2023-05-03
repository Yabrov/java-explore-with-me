package ru.practicum.main.server.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.main.server.model.enums.RequestState;

import java.util.Set;

@Getter
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class EventRequestStatusUpdateRequest {
    private Set<Long> requestIds;
    private RequestState status;
}
