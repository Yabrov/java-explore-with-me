package ru.practicum.stats.server.model.mappers.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.server.model.EndpointHit;

@Component
public class EndpointHitDtoMapper implements Converter<EndpointHitDto, EndpointHit> {

    @Override
    public EndpointHit convert(EndpointHitDto source) {
        return new EndpointHit(
                null,
                source.getApp(),
                source.getUri(),
                source.getIp(),
                source.getTimestamp());
    }
}
