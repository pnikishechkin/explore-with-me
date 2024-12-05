package ru.practicum.ewm.main.event.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.ewm.main.category.Category;
import ru.practicum.ewm.main.event.EventState;
import ru.practicum.ewm.main.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annotation", nullable = false)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    private String description;

    @Column(name = "date_event")
    private LocalDateTime eventDate;
    @Column(name = "date_created")
    private LocalDateTime createdOn;
    @Column(name = "date_published")
    private LocalDateTime publishedDate;
    private Integer participantLimit;
    private Boolean requestModeration;

    private EventState state;

    @Embedded
    private Location location;

    @Column(name = "paid", nullable = false)
    private Boolean paid;

    @Column(name = "title", nullable = false)
    private String title;
}
