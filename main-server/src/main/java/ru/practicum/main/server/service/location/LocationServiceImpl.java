package ru.practicum.main.server.service.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.server.dto.location.AllowedLocationDto;
import ru.practicum.main.server.dto.location.NewLocationRequest;
import ru.practicum.main.server.exception.LocationCreationException;
import ru.practicum.main.server.model.entities.AllowedLocation;
import ru.practicum.main.server.model.entities.Location;
import ru.practicum.main.server.repository.location.LocationRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final Converter<AllowedLocation, AllowedLocationDto> allowedLocationMapper;
    private final Converter<NewLocationRequest, AllowedLocation> locationRequestMapper;

    @Transactional
    @Override
    public Location createLocation(Location location, boolean checkZone) {
        try {
            if (checkZone) {
                if (locationRepository.isLocationInsideAllowedZone(location)) {
                    log.info("Location is in allowed zone");
                    return locationRepository.saveLocation(location);
                } else {
                    log.error("Location is beyond allowed zone");
                    throw new LocationCreationException(location);
                }
            } else {
                return locationRepository.saveLocation(location);
            }
        } catch (Exception e) {
            log.info("{} already exists", location);
            return locationRepository
                    .findLocationByCoord(location.getLongitude(), location.getLatitude())
                    .orElse(location);
        }
    }

    @Transactional
    @Override
    public AllowedLocationDto addAllowedLocation(NewLocationRequest request) {
        AllowedLocation allowedLocation = locationRequestMapper.convert(request);
        return allowedLocationMapper.convert(locationRepository.saveAllowedLocation(allowedLocation));
    }
}
