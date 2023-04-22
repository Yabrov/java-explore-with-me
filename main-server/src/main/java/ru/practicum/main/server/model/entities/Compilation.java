package ru.practicum.main.server.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "compillations")
public class Compilation extends BaseEntity<Long> {

    public Compilation(Long id, Boolean pinned, String title) {
        this.id = id;
        this.pinned = pinned;
        this.title = title;
    }

    @ManyToMany(mappedBy = "compilations", fetch = FetchType.EAGER)
    private Set<Event> events = new HashSet<>();

    @Column(name = "pinned", nullable = false)
    private Boolean pinned;

    @Column(name = "title", nullable = false)
    private String title;
}
