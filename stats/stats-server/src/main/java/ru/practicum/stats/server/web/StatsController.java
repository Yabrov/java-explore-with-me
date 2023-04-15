package ru.practicum.stats.server.web;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.server.service.StatsHistoryService;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsHistoryService statsHistoryService;

    @PostMapping("/hit")
    public EndpointHitDto createHit(@RequestBody EndpointHitDto endpointHitDto) {
        return statsHistoryService.saveHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public Collection<ViewStatsDto> getStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(defaultValue = "", required = false) Collection<String> uris,
            @RequestParam(defaultValue = "false", required = false) Boolean unique) {
        return statsHistoryService.getStats(start, end, uris, unique);
    }
}
