package ru.practicum.ewm.main.request;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.ewm.main.event.model.Event;
import ru.practicum.ewm.main.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Data
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "status")
    private RequestStatus status;
}
