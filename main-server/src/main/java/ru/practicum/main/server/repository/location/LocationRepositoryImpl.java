package ru.practicum.main.server.repository.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.main.server.model.entities.Location;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

    private final LocationJpaRepository jpaRepository;

    @Override
    public Optional<Location> findLocationByCoord(float lon, float lat) {
        return jpaRepository.findByLongitudeAndLatitude(lon, lat);
    }

    @Override
    public Location saveLocation(Location location) {
        return jpaRepository.save(location);
    }
}
