package ru.practicum.main.server.repository.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.main.server.model.entities.Location;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

    private final LocationJpaRepository locationJpaRepository;

    @Override
    public Optional<Location> findLocationByCoord(float lon, float lat) {
        return locationJpaRepository.findByLongitudeAndLatitude(lon, lat);
    }

    @Override
    public Location saveLocation(Location location) {
        return locationJpaRepository.save(location);
    }
}
