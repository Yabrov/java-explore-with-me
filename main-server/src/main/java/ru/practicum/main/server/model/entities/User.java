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
@Table(name = "users")
public class User extends BaseEntity<Long> {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", length = 512, unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "initiator")
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "requester")
    private List<ParticipationRequest> requests = new ArrayList<>();

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
