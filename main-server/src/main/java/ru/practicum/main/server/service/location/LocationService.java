package ru.practicum.main.server.service.location;

import ru.practicum.main.server.dto.location.AllowedLocationDto;
import ru.practicum.main.server.dto.location.NewLocationRequest;
import ru.practicum.main.server.model.entities.Location;

public interface LocationService {

    Location createLocation(Location location);

    AllowedLocationDto addAllowedLocation(NewLocationRequest request);
}
