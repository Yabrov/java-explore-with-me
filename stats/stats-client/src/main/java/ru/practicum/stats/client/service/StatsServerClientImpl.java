package ru.practicum.stats.client.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.stats.client.exception.StatsClientException;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;

@Slf4j
public class StatsServerClientImpl extends BaseClient implements StatsServerClient {

    private final ObjectMapper objectMapper;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StatsServerClientImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate);
        this.objectMapper = objectMapper;
    }

    @Override
    public EndpointHitDto sendEndpointHit(EndpointHitDto endpointHitDto) {
        try {
            ResponseEntity<Object> response = post("/hit", endpointHitDto);
            return objectMapper.convertValue(response.getBody(), EndpointHitDto.class);
        } catch (IllegalArgumentException e) {
            String mes = "Unable to save hit";
            log.error(mes, e);
            throw new StatsClientException(mes, e);
        }
    }

    @Override
    public Collection<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, Collection<String> uris, Boolean unique) {
        try {
            Map<String, Object> parameters = Map.of(
                    "start", start.format(formatter),
                    "end", end.format(formatter),
                    "uris", uris == null ? "" : String.join(",", uris),
                    "unique", unique == null ? "" : unique
            );
            ResponseEntity<Object> response = get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
            return objectMapper.convertValue(response.getBody(), new TypeReference<>() {});
        } catch (IllegalArgumentException e) {
            String mes = "Unable to get stats";
            log.error(mes, e);
            throw new StatsClientException(mes, e);
        }
    }
}
