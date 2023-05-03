package ru.practicum.main.server.service.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.server.model.entities.Location;
import ru.practicum.main.server.repository.location.LocationRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Transactional
    @Override
    public Location createLocation(Location location) {
        try {
            return locationRepository.saveLocation(location);
        } catch (Exception e) {
            log.info("{} already exists", location);
            return locationRepository
                    .findLocationByCoord(location.getLongitude(), location.getLatitude())
                    .orElse(location);
        }
    }
}
