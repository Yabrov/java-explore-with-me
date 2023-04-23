package ru.practicum.main.server.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import ru.practicum.main.server.model.enums.EventState;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name = "events")
public class Event extends BaseEntity<Long> {

    public Event(Long id,
                 String annotation,
                 Category category,
                 String description,
                 LocalDateTime eventDate,
                 User initiator,
                 Location location,
                 Boolean paid,
                 Integer participantLimit,
                 Boolean requestModeration,
                 String title) {
        this.id = id;
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.requestModeration = requestModeration;
        this.title = title;
    }

    @Column(
            name = "annotation",
            length = 2000,
            nullable = false
    )
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private List<ParticipationRequest> requests = new ArrayList<>();

    @Column(
            name = "created_on",
            nullable = false,
            columnDefinition = "timestamp"
    )
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(
            name = "description",
            length = 7000,
            nullable = false
    )
    private String description;

    @Column(
            name = "event_date",
            nullable = false,
            columnDefinition = "timestamp"
    )
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(
            name = "paid",
            nullable = false
    )
    private Boolean paid;

    @Column(
            name = "participant_limit",
            nullable = false
    )
    private Integer participantLimit;

    @Column(
            name = "published_on",
            nullable = false,
            columnDefinition = "timestamp"
    )
    private LocalDateTime publishedOn;

    @Column(
            name = "request_moderation",
            nullable = false
    )
    private Boolean requestModeration;

    @Column(
            name = "state",
            length = 10,
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private EventState state;

    @Column(
            name = "title",
            length = 120,
            nullable = false
    )
    private String title;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "compilation_events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "compilation_id")
    )
    private Set<Compilation> compilations = new HashSet<>();
}
