package ru.practicum.main.server.repository.location;

import ru.practicum.main.server.model.entities.Location;

import java.util.Optional;

public interface LocationRepository {

    Optional<Location> findLocationByCoord(float lon, float lat);

    Location saveLocation(Location location);
}
