package ru.practicum.stats.server.model.mappers.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.server.model.ViewStats;

@Component
public class ViewStatsMapper implements Converter<ViewStats, ViewStatsDto> {

    @Override
    public ViewStatsDto convert(ViewStats source) {
        return new ViewStatsDto(source.getApp(), source.getUri(), source.getHits());
    }
}
