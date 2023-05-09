package ru.practicum.main.server.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.main.server.model.enums.LocationType;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "allowed_locations")
public class AllowedLocation extends BaseEntity<Long> {

    @Column(name = "lon", nullable = false, columnDefinition = "real")
    private float longitude;

    @Column(name = "lat", nullable = false, columnDefinition = "real")
    private float latitude;

    @Column(name = "rad", nullable = false, columnDefinition = "real")
    private float radius;

    @Column(name = "type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private LocationType type;

    @Column(name = "name", nullable = false)
    private String name;

    public AllowedLocation(Long id,
                           float longitude,
                           float latitude,
                           float radius,
                           LocationType type,
                           String name
    ) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
        this.type = type;
        this.name = name;
    }
}
