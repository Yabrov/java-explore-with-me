package ru.practicum.main.server.repository.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.server.model.entities.AllowedLocation;
import ru.practicum.main.server.model.entities.Location;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

    private final LocationJpaRepository locationJpaRepository;
    private final AllowedLocationJpaRepository allowedLocationJpaRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Location> findLocationByCoord(float lon, float lat) {
        return locationJpaRepository.findByLongitudeAndLatitude(lon, lat);
    }

    @Transactional
    @Override
    public Location saveLocation(Location location) {
        return locationJpaRepository.save(location);
    }

    @Override
    public AllowedLocation saveAllowedLocation(AllowedLocation allowedLocation) {
        return allowedLocationJpaRepository.save(allowedLocation);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean isLocationInsideAllowedZone(Location location) {
        return allowedLocationJpaRepository
                .isLocationInsideAllowedZone(location.getLongitude(), location.getLatitude()) > 0;
    }

    @Override
    public Collection<Long> findAllLocationsInsideZone(float longitude, float latitude, float radius) {
        return locationJpaRepository.findAllLocationsInsideZone(longitude, latitude, radius);
    }
}
