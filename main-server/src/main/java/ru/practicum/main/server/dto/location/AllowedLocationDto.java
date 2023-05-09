package ru.practicum.main.server.dto.location;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.main.server.model.enums.LocationType;

@Getter
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class AllowedLocationDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "lon")
    private float longitude;

    @JsonProperty(value = "lat")
    private float latitude;

    @JsonProperty(value = "rad")
    private float radius;

    @JsonProperty(value = "type")
    private LocationType type;

    @JsonProperty(value = "name")
    private String name;
}
