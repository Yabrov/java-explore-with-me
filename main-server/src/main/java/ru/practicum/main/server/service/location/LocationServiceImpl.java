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
import ru.practicum.main.server.exception.LocationDeleteException;
import ru.practicum.main.server.exception.LocationNotFoundException;
import ru.practicum.main.server.model.entities.AllowedLocation;
import ru.practicum.main.server.model.entities.Event;
import ru.practicum.main.server.model.entities.Location;
import ru.practicum.main.server.repository.event.EventRepository;
import ru.practicum.main.server.repository.location.LocationRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
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

    @Transactional(readOnly = true)
    @Override
    public Collection<AllowedLocationDto> getAllAllowedLocations() {
        return locationRepository
                .findAllAllowedLocations()
                .stream()
                .map(allowedLocationMapper::convert)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public AllowedLocationDto getAllowedLocationById(Long id) {
        AllowedLocation location = locationRepository.findAllowedLocationById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
        return allowedLocationMapper.convert(location);
    }

    @Transactional
    @Override
    public AllowedLocationDto updateAllowedLocation(Long id, AllowedLocationDto dto) {
        AllowedLocation location = locationRepository.findAllowedLocationById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
        if (dto.getType() != null) {
            location.setType(dto.getType());
        }
        if (dto.getName() != null) {
            location.setName(dto.getName());
        }
        return allowedLocationMapper.convert(locationRepository.saveAllowedLocation(location));
    }

    @Transactional
    @Override
    public void deleteAllowedLocation(Long id) {
        AllowedLocation location = locationRepository.findAllowedLocationById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
        Collection<Event> events = eventRepository.findAllEventsInsideZone(
                location.getLongitude(),
                location.getLatitude(),
                location.getRadius()
        );
        if (events.isEmpty()) {
            locationRepository.deleteAllowedLocation(id);
        } else {
            throw new LocationDeleteException(id);
        }
    }
}
