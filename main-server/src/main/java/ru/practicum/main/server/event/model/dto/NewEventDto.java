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
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class NewEventDto {

    @NotNull
    @Length(min = 20, max = 2000)
    @JsonProperty(required = true)
    private String annotation;

    @NotNull
    @JsonProperty(value = "category", required = true)
    private Long categoryId;

    @NotNull
    @Length(min = 20, max = 7000)
    @JsonProperty(required = true)
    private String description;

    @NotNull
    @JsonProperty(required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventDate;

    @NotNull
    @JsonProperty(required = true)
    private Location location;

    @JsonProperty(defaultValue = "false")
    private Boolean paid;

    @JsonProperty(defaultValue = "0")
    private Integer participantLimit;

    @JsonProperty(defaultValue = "true")
    private Boolean requestModeration;

    @NotNull
    @JsonProperty(required = true)
    @Length(min = 3, max = 120)
    private String title;
}
