package ru.practicum.main.server.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.main.server.config.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity<Long> {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(
            name = "email",
            length = 512,
            unique = true,
            nullable = false
    )
    private String email;

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
