package ru.practicum.main.server.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.main.server.model.entities.AllowedLocation;

@Repository
public interface AllowedLocationJpaRepository extends JpaRepository<AllowedLocation, Long> {

    @Query(value = "SELECT count(a) FROM allowed_locations a " +
            "WHERE 1000 * distance(a.lat, a.lon, :lat, :lon) <= a.rad LIMIT 1",
            nativeQuery = true)
    Integer isLocationInsideAllowedZone(float lon, float lat);
}
