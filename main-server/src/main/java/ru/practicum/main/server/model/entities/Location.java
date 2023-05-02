package ru.practicum.main.server.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "locations")
public class Location extends BaseEntity<Long> {

    public Location(Long id, float longitude, float latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Column(name = "lon", nullable = false, columnDefinition = "real")
    private float longitude;

    @Column(name = "lat", nullable = false, columnDefinition = "real")
    private float latitude;

    @OneToMany(mappedBy = "location")
    private List<Event> events = new ArrayList<>();
}
