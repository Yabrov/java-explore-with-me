package ru.practicum.stats.client.service;

import org.springframework.lang.Nullable;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatsServerClient {

    EndpointHitDto sendEndpointHit(EndpointHitDto endpointHitDto);

    Collection<ViewStatsDto> getStats(LocalDateTime start,
                          LocalDateTime end,
                          @Nullable Collection<String> uris,
                          @Nullable Boolean unique);
}
