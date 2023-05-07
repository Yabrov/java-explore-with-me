package ru.practicum.main.server.service.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Override
    public Location createLocation(Location location, boolean checkZone) {
        if (checkZone && !locationRepository.isLocationInsideAllowedZone(location)) {
            log.error("Location is beyond allowed zone");
            throw new LocationCreationException(location);
        } else {
            try {
                return locationRepository.saveLocation(location);
            } catch (DataIntegrityViolationException e) {
                return locationRepository
                        .findLocationByCoord(location.getLongitude(), location.getLatitude())
                        .get();
            }
        }
    }

    @Transactional
    @Override
    public AllowedLocationDto addAllowedLocation(NewLocationRequest request) {
        AllowedLocation allowedLocation = locationRequestMapper.convert(request);
        return allowedLocationMapper.convert(locationRepository.saveAllowedLocation(allowedLocation));
    }
}
