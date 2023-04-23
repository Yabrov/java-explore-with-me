package ru.practicum.main.server.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import ru.practicum.main.server.model.enums.RequestState;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name = "requests")
public class ParticipationRequest extends BaseEntity<Long> {

    public ParticipationRequest(Long id,
                                String description,
                                Event event,
                                User requester) {
        this.id = id;
        this.description = description;
        this.event = event;
        this.requester = requester;
    }

    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "text"
    )
    private String description;

    @Column(
            name = "created",
            nullable = false,
            columnDefinition = "timestamp"
    )
    @CreationTimestamp
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @Column(
            name = "status",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private RequestState status;
}
