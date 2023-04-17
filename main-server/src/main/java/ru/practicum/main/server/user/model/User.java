package ru.practicum.main.server.user.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.server.config.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity<Long> {
    private String name;
    private String email;
}
