package ru.practicum.main.server.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.server.model.entities.Location;

import java.util.Optional;

public interface LocationJpaRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByLongitudeAndLatitude(float longitude, float latitude);
}
