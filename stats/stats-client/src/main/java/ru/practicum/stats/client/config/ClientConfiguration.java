package ru.practicum.stats.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.stats.client.service.StatsServerClient;
import ru.practicum.stats.client.service.StatsServerClientImpl;

@Configuration
public class ClientConfiguration {

    @Bean
    public RestTemplate getRestTemplate(@Value("${stats-server.url}") String serverUrl,
                                        RestTemplateBuilder builder) {
        return builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    @Bean
    public StatsServerClient getClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        return new StatsServerClientImpl(restTemplate, objectMapper);
    }
}
