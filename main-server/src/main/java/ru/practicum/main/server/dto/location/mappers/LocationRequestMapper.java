package ru.practicum.main.server.dto.location.mappers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.dto.location.NewLocationRequest;
import ru.practicum.main.server.model.entities.AllowedLocation;

@Component
public class LocationRequestMapper implements Converter<NewLocationRequest, AllowedLocation> {

    @Override
    public AllowedLocation convert(NewLocationRequest source) {
        return new AllowedLocation(
                null,
                source.getLongitude(),
                source.getLatitude(),
                source.getRadius(),
                source.getType(),
                source.getName()
        );
    }
}
