package ru.practicum.main.server.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.practicum.stats.client.service.StatsServerClient;
import ru.practicum.stats.dto.EndpointHitDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

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

    @Async
    @Before(value = "statsCollectingTrigger(request)", argNames = "jp,request")
    public void aspect(JoinPoint jp, HttpServletRequest request) {
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
}
