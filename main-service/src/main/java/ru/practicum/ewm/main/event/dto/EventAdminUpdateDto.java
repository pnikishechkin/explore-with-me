package ru.practicum.ewm.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.ewm.main.event.EventAdminStateAction;
import ru.practicum.ewm.main.event.Location;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class EventAdminUpdateDto {
    private Long id;

    @Size(min = 20, max = 2000)
    private String annotation;
    private Long categoryId;

    @Size(min = 20, max = 7000)
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    @Min(value = 0)
    private Integer participantLimit;
    private Boolean requestModeration;
    //private String stateAction;
    private EventAdminStateAction stateAction;

    @Size(min = 3, max = 120)
    private String title;
}
