package ru.practicum.ewm.main.event.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Location {
    @Column(name = "lat")
    private double lat;
    @Column(name = "lon")
    private double lon;
}
