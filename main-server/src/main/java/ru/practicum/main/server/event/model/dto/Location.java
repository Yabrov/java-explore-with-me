package ru.practicum.main.server.event.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class Location {

    @JsonProperty("lon")
    private float longitude;

    @JsonProperty("lat")
    private float latitude;
}
