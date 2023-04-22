package ru.practicum.main.server.dto.location;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class LocationDto {

    @JsonProperty("lon")
    private float longitude;

    @JsonProperty("lat")
    private float latitude;
}
