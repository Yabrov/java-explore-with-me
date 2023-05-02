package ru.practicum.main.server.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name = "compilations")
public class Compilation extends BaseEntity<Long> {

    public Compilation(Long id,
                       Boolean pinned,
                       String title,
                       Collection<Event> events) {
        this.id = id;
        this.pinned = pinned;
        this.title = title;
        this.events.addAll(events);
    }

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "compilations",
            fetch = FetchType.EAGER)
    private Set<Event> events = new HashSet<>();

    @Column(name = "pinned", nullable = false)
    private Boolean pinned;

    @Column(name = "title", unique = true, nullable = false)
    private String title;
}
