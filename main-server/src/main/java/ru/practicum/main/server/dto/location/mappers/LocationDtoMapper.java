package ru.practicum.main.server.dto.location.mappers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.dto.location.LocationDto;
import ru.practicum.main.server.model.entities.Location;

@Component
public class LocationDtoMapper implements Converter<LocationDto, Location> {

    @Override
    public Location convert(LocationDto source) {
        return new Location(null, source.getLongitude(), source.getLatitude());
    }
}
