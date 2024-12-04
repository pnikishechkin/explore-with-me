package ru.practicum.ewm.main.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class EventParams {
    private String text;
    private List<Long> categories;
    private Boolean paid;
    private String rangeStart;
    private String rangeEnd;
    private Boolean onlyAvailable = false;
    private String sort;
    private Integer from;
    private Integer size;
}
