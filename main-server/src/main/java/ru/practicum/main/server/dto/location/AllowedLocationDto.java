package ru.practicum.main.server.dto.location;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.main.server.model.enums.LocationType;

@Getter
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class AllowedLocationDto {

    @JsonProperty(value = "id", required = true)
    private Long id;

    @JsonProperty(value = "lon", required = true)
    private float longitude;

    @JsonProperty(value = "lat", required = true)
    private float latitude;

    @JsonProperty(value = "rad", required = true)
    private float radius;

    @JsonProperty(value = "type", required = true)
    private LocationType type;

    @JsonProperty(value = "name", required = true)
    private String name;
}
