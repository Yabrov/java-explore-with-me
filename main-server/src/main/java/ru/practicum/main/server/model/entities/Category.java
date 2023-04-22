package ru.practicum.main.server.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name = "categories")
public class Category extends BaseEntity<Long> {

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Event> events = new ArrayList<>();
}
