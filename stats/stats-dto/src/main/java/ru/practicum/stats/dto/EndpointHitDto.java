package ru.practicum.stats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EndpointHitDto {

    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;

    public EndpointHitDto(
            String app,
            String uri,
            String ip,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @JsonSerialize(using = LocalDateTimeSerializer.class)
            @JsonDeserialize(using = LocalDateTimeDeserializer.class)
            LocalDateTime timestamp) {
        this.app = app;
        this.uri = uri;
        this.ip = ip;
        this.timestamp = timestamp;
    }
}
