package ru.practicum.main.server.exception;

import ru.practicum.main.server.model.entities.Location;

public class LocationCreationException extends RuntimeException {

    private static final String PATTERN = "Location {%f, %f} is beyond allowed zone";

    public LocationCreationException(Location location) {
        super(String.format(PATTERN, location.getLongitude(), location.getLatitude()));
    }
}
