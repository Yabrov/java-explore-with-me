package ru.practicum.stats.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.stats.client.config.ClientConfiguration;
import ru.practicum.stats.client.service.StatsServerClient;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@AutoConfigureWebClient
@SpringBootTest(classes = ClientConfiguration.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class StatsClientTest {

    private final StatsServerClient client;
    private final LocalDateTime start = LocalDateTime.of(2022, 4, 1, 0, 0, 0);
    private final LocalDateTime end = LocalDateTime.of(2022, 4, 2, 0, 0, 0);

    @Test
    @Order(1)
    void createEndpointHitTest() {
        log.info("Initializing...");
        LocalDateTime timestamp1 = LocalDateTime.of(2022, 4, 1, 0, 0, 0);
        LocalDateTime timestamp2 = LocalDateTime.of(2022, 4, 2, 0, 0, 0);
        LocalDateTime timestamp3 = LocalDateTime.of(2022, 4, 3, 0, 0, 0);
        EndpointHitDto dto1 = new EndpointHitDto("app1", "/event/1", "192.168.0.1", timestamp1);
        EndpointHitDto dto2 = new EndpointHitDto("app1", "/event/2", "192.168.0.1", timestamp2);
        EndpointHitDto dto3 = new EndpointHitDto("app1", "/event/2", "192.168.0.1", timestamp3);
        assertEquals(client.sendEndpointHit(dto1), dto1);
        assertEquals(client.sendEndpointHit(dto2), dto2);
        assertEquals(client.sendEndpointHit(dto3), dto3);
    }

    @Test
    @Order(2)
    void getAllStatsTest() {
        Collection<ViewStatsDto> stats = client.getStats(start, end, null, null);
        assertThat(stats).asList().hasSize(2);
    }

    @Test
    @Order(3)
    void getAllStatsWithUniqueTest() {
        Collection<ViewStatsDto> stats = client.getStats(start, end, null, true);
        assertThat(stats).asList().hasSize(2).first();
    }

    @Test
    @Order(4)
    void getAllStatsByUrisTest() {
        Collection<String> uris = List.of("/event/2");
        Collection<ViewStatsDto> stats = client.getStats(start, end, uris, null);
        assertThat(stats).asList().hasSize(1);
    }

    @Test
    @Order(5)
    void getAllStatsByUrisWithUniqueTest() {
        Collection<String> uris = List.of("/event/2");
        Collection<ViewStatsDto> stats = client.getStats(start, end, uris, true);
        assertThat(stats).asList().hasSize(1);
    }
}
