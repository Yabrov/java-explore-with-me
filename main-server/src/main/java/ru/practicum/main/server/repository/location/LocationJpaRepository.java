package ru.practicum.main.server.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.main.server.model.entities.Location;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface LocationJpaRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByLongitudeAndLatitude(float longitude, float latitude);

    @Query(value = "SELECT l.id FROM locations l WHERE 1000 * distance(l.lat, l.lon, :latitude, :longitude) <= :radius", nativeQuery = true)
    Collection<Long> findAllLocationsInsideZone(float longitude, float latitude, float radius);
}
