package ru.practicum.main.server.dto.location;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.main.server.model.enums.LocationType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class NewLocationRequest {

    @NotNull
    @JsonProperty(value = "lon", required = true)
    private float longitude;

    @NotNull
    @JsonProperty(value = "lat", required = true)
    private float latitude;

    @NotNull
    @JsonProperty(value = "rad", required = true)
    private float radius;

    @NotNull
    @JsonProperty(value = "type", required = true)
    private LocationType type;

    @NotNull
    @NotBlank
    @JsonProperty(value = "name", required = true)
    private String name;
}
