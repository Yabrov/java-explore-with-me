package ru.practicum.main.server.dto.location.mappers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.dto.location.AllowedLocationDto;
import ru.practicum.main.server.model.entities.AllowedLocation;

@Component
public class AllowedLocationMapper implements Converter<AllowedLocation, AllowedLocationDto> {

    @Override
    public AllowedLocationDto convert(AllowedLocation source) {
        return new AllowedLocationDto(
                source.getId(),
                source.getLongitude(),
                source.getLatitude(),
                source.getRadius(),
                source.getType(),
                source.getName()
        );
    }
}
