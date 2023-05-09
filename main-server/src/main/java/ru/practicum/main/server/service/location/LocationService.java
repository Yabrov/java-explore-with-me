package ru.practicum.main.server.service.location;

import ru.practicum.main.server.dto.location.AllowedLocationDto;
import ru.practicum.main.server.dto.location.NewLocationRequest;
import ru.practicum.main.server.model.entities.Location;

import java.util.Collection;

public interface LocationService {

    Location createLocation(Location location, boolean checkZone);

    AllowedLocationDto addAllowedLocation(NewLocationRequest request);

    Collection<AllowedLocationDto> getAllAllowedLocations();

    AllowedLocationDto getAllowedLocationById(Long id);

    AllowedLocationDto updateAllowedLocation(Long id, AllowedLocationDto dto);

    void deleteAllowedLocation(Long id);
}
