package ru.practicum.main.server.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.model.entities.Event;
import ru.practicum.main.server.model.enums.EventState;
import ru.practicum.stats.client.service.StatsServerClient;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class StatsServerAspect {

    private final StatsServerClient statsServerClient;

    private static final String APPLICATION_NAME = "ewm-main-service";

    @Pointcut(value = "execution(public * ru.practicum.main.server.controller.pub.EventPublicController.*(..)) && args(.., request))")
    public void statsCollectingTrigger(HttpServletRequest request) {
    }

    @Pointcut(value = "execution(public * ru.practicum.main.server.dto.event.mappers.*.convert(ru.practicum.main.server.model.entities.Event)) && args(source))")
    public void statsGettingTrigger(Event source) {
    }

    @Async
    @Before(value = "statsCollectingTrigger(request)", argNames = "jp,request")
    public void saveEndpointHitAspect(JoinPoint jp, HttpServletRequest request) {
        try {
            EndpointHitDto endpointHitDto = new EndpointHitDto(
                    APPLICATION_NAME,
                    request.getRequestURI(),
                    request.getRemoteHost(),
                    LocalDateTime.now()
            );
            EndpointHitDto response = statsServerClient.sendEndpointHit(endpointHitDto);
            log.info("Saved stats: {}", response.toString());
        } catch (Exception e) {
            log.error("Error when sending stats", e);
        }
    }

    @Before(value = "statsGettingTrigger(event)", argNames = "jp,event")
    public void getViewsAspect(JoinPoint jp, Event event) {
        Collection<ViewStatsDto> views = event.getState() == EventState.PUBLISHED
                ? statsServerClient.getStats(
                event.getPublishedOn(),
                LocalDateTime.now(),
                List.of("/event/" + event.getId()),
                false)
                : Collections.emptyList();
        event.setViews(views.size());
    }
}
