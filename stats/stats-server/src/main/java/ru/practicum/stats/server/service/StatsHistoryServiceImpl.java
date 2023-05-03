package ru.practicum.stats.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.server.model.EndpointHit;
import ru.practicum.stats.server.model.ViewStats;
import ru.practicum.stats.server.repository.StatsHistoryRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsHistoryServiceImpl implements StatsHistoryService {

    private final StatsHistoryRepository statsHistoryRepository;
    private final Converter<ViewStats, ViewStatsDto> statsInfoMapper;
    private final Converter<EndpointHitDto, EndpointHit> endpointHitMapper;
    private final Converter<EndpointHit, EndpointHitDto> statsHistoryMapper;

    @Override
    public EndpointHitDto saveHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = endpointHitMapper.convert(endpointHitDto);
        return statsHistoryMapper.convert(statsHistoryRepository.saveStat(endpointHit));
    }

    @Override
    public Collection<ViewStatsDto> getStats(LocalDateTime from,
                                             LocalDateTime to,
                                             Collection<String> uris,
                                             Boolean uniqueIPsOnly) {
        Collection<ViewStats> history = new LinkedList<>();
        if (uris == null || uris.isEmpty()) {
            history.addAll(uniqueIPsOnly.equals(Boolean.TRUE)
                    ? statsHistoryRepository.getStatsInPeriodWithUniqIps(from, to)
                    : statsHistoryRepository.getStatsInPeriod(from, to));
        } else {
            history.addAll(uniqueIPsOnly.equals(Boolean.TRUE)
                    ? statsHistoryRepository.getStatInPeriodByUrisWithUniqIps(uris, from, to)
                    : statsHistoryRepository.getStatInPeriodByUris(uris, from, to));
        }
        return history.stream().map(statsInfoMapper::convert).collect(Collectors.toList());
    }
}
