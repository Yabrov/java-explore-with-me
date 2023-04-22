package ru.practicum.main.server.dto.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.main.server.dto.category.CategoryDto;
import ru.practicum.main.server.dto.location.LocationDto;
import ru.practicum.main.server.dto.user.UserShortDto;
import ru.practicum.main.server.model.enums.EventState;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class EventFullDto {

    private Long id;

    @JsonProperty(required = true)
    private String annotation;

    @JsonProperty(required = true)
    private CategoryDto category;

    private Long confirmedRequests;

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

    @JsonProperty(required = true)
    private UserShortDto initiator;

    @JsonProperty(required = true)
    private LocationDto location;

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

    private Long views;
}
