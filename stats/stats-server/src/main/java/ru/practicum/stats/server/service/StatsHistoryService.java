package ru.practicum.stats.server.service;

import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatsHistoryService {

    EndpointHitDto saveHit(EndpointHitDto endpointHitDto);

    Collection<ViewStatsDto> getStats(LocalDateTime from, LocalDateTime to, Collection<String> uris, Boolean isUniqIps);
}
