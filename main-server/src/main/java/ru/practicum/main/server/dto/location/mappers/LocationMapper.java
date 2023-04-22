package ru.practicum.main.server.dto.location.mappers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.dto.location.LocationDto;
import ru.practicum.main.server.model.entities.Location;

@Component
public class LocationMapper implements Converter<Location, LocationDto> {

    @Override
    public LocationDto convert(Location source) {
        return new LocationDto(source.getLongitude(), source.getLatitude());
    }
}
