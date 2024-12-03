package ru.practicum.ewm.main.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.event.dto.EventFullDto;
import ru.practicum.ewm.main.event.dto.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventControllerPublic {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(required = false) String text,
                                         @RequestParam(required = false) List<Long> categories,
                                         @RequestParam(required = false) Boolean paid,
                                         @RequestParam(required = false) String rangeStart,
                                         @RequestParam(required = false) String rangeEnd,
                                         @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                         @RequestParam(required = false) String sort,
                                         @RequestParam(defaultValue = "0") Integer from,
                                         @RequestParam(defaultValue = "10") Integer size) {
        EventParams eventParams = new EventParams();
        eventParams.setText(text);
        eventParams.setCategories(categories);
        eventParams.setPaid(paid);
        eventParams.setRangeStart(rangeStart);
        eventParams.setRangeEnd(rangeEnd);
        eventParams.setOnlyAvailable(onlyAvailable);
        eventParams.setSort(sort);
        eventParams.setFrom(from);
        eventParams.setSize(size);

        return eventService.getEventsByParams(eventParams);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getPublicEvent(@PathVariable Long eventId) {
        return eventService.getPublicEventById(eventId);
    }
}
