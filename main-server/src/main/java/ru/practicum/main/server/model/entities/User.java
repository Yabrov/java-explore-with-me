package ru.practicum.main.server.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity<Long> {

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Column(name = "name", nullable = false)
    private String name;

    @Column(
            name = "email",
            length = 512,
            unique = true,
            nullable = false
    )
    private String email;

    @OneToMany(mappedBy = "initiator", fetch = FetchType.LAZY)
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "requester", fetch = FetchType.LAZY)
    private List<ParticipationRequest> requests = new ArrayList<>();
}
