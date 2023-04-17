package ru.practicum.stats.server.model.mappers.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.server.model.EndpointHit;

@Component
public class EndpointHitMapper implements Converter<EndpointHit, EndpointHitDto> {

    @Override
    public EndpointHitDto convert(EndpointHit source) {
        return new EndpointHitDto(
                source.getApp(),
                source.getUri(),
                source.getIp(),
                source.getTimestamp());
    }
}
