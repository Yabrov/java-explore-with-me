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
import org.hibernate.validator.constraints.Length;
import ru.practicum.main.server.dto.location.LocationDto;
import ru.practicum.main.server.model.enums.UserEventState;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class UpdateEventUserRequest {

    @Length(min = 20, max = 2000)
    private String annotation;

    @JsonProperty("category")
    private Long categoryId;

    @Length(min = 20, max = 7000)
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventDate;

    private LocationDto location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private UserEventState stateAction;

    @Length(min = 3, max = 120)
    private String title;
}
