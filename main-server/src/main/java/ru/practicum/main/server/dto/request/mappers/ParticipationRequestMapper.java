package ru.practicum.main.server.dto.request.mappers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.dto.request.ParticipationRequestDto;
import ru.practicum.main.server.model.entities.ParticipationRequest;

@Component
public class ParticipationRequestMapper implements Converter<ParticipationRequest, ParticipationRequestDto> {

    @Override
    public ParticipationRequestDto convert(ParticipationRequest source) {
        return new ParticipationRequestDto(
                source.getId(),
                source.getCreated(),
                source.getEvent().getId(),
                source.getRequester().getId(),
                source.getStatus().name()
        );
    }
}
