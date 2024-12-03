package ru.practicum.ewm.main.event;

import lombok.Data;

import java.util.List;

@Data
public class EventAdminParams {
    private List<Long> usersIds;
    private List<String> states;
    private List<Long> categoriesIds;
    private String rangeStart;
    private String rangeEnd;
    private Integer from;
    private Integer size;
}
