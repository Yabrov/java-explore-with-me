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
@Table(
        name = "requests",
        indexes = {
                @Index(name = "event_id_idx", columnList = "event_id"),
                @Index(name = "user_id_idx", columnList = "requester_id")
        }
)
public class ParticipationRequest extends BaseEntity<Long> {

    @Column(name = "created", nullable = false, columnDefinition = "timestamp")
    @CreationTimestamp
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @Column(name = "status", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestState status;

    public ParticipationRequest(Long id,
                                Event event,
                                User requester) {
        this.id = id;
        this.event = event;
        this.requester = requester;
        this.status = RequestState.PENDING;
    }
}
