package ru.practicum.ewm.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.ewm.main.category.Category;
import ru.practicum.ewm.main.event.EventState;
import ru.practicum.ewm.main.event.Location;
import ru.practicum.ewm.main.user.User;

import java.time.LocalDateTime;

@Data
@ToString
public class EventFullDto {
    private Long id;

    private Integer confirmedRequests;
    private Integer views;

    private Category category;
    private User initiator;

    private String title;
    private String annotation;
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;

    private EventState state;
}
