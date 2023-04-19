package ru.practicum.main.server.event.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.main.server.category.model.dto.CategoryDto;
import ru.practicum.main.server.event.model.EventState;
import ru.practicum.main.server.user.model.dto.UserShortDto;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class EventFullDto {

    @JsonProperty(required = true)
    private String annotation;

    @JsonProperty(required = true)
    private CategoryDto category;

    private Integer confirmedRequests;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdOn;

    private String description;

    @JsonProperty(required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventDate;

    private Long id;

    @JsonProperty(required = true)
    private UserShortDto initiator;

    @JsonProperty(required = true)
    private Location location;

    @JsonProperty(required = true)
    private Boolean paid;

    private Integer participantLimit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime publishedOn;

    @JsonProperty(defaultValue = "true")
    private Boolean requestModeration;

    private EventState state;

    @JsonProperty(required = true)
    private String title;

    private Integer views;
}
