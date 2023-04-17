package ru.practicum.stats.server.repository;

import ru.practicum.stats.server.model.EndpointHit;
import ru.practicum.stats.server.model.ViewStats;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatsHistoryRepository {

    EndpointHit saveStat(EndpointHit endpointHit);

    Collection<ViewStats> getStatsInPeriod(LocalDateTime from, LocalDateTime to);

    Collection<ViewStats> getStatsInPeriodWithUniqIps(LocalDateTime from, LocalDateTime to);

    Collection<ViewStats> getStatInPeriodByUris(Collection<String> uris, LocalDateTime from, LocalDateTime to);

    Collection<ViewStats> getStatInPeriodByUrisWithUniqIps(Collection<String> uris, LocalDateTime from, LocalDateTime to);
}
