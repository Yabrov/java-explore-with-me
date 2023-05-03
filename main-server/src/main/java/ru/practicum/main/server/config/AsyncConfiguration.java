package ru.practicum.main.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfiguration {

    @Bean
    public TaskExecutor getTaskExecutor(@Value("${async.core-size}") Integer coreSize,
                                        @Value("${async.pool-size}") Integer maxPoolSize,
                                        @Value("${async.queue-size}") Integer queueCapacity) {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(coreSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);
        return taskExecutor;
    }
}
