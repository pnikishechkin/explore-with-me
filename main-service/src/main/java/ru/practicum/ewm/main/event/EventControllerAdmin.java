package ru.practicum.ewm.main.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.event.dto.EventAdminUpdateDto;
import ru.practicum.ewm.main.event.dto.EventFullDto;

import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventControllerAdmin {

    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(required = false) List<Long> users,
                                    @RequestParam(required = false) List<String> states,
                                    @RequestParam(required = false) List<Long> categories,
                                    @RequestParam(required = false) String rangeStart,
                                    @RequestParam(required = false) String rangeEnd,
                                    @RequestParam(defaultValue = "0") Integer from,
                                    @RequestParam(defaultValue = "10") Integer size) {
        EventAdminParams eap = new EventAdminParams();
        eap.setUsersIds(users);
        eap.setStates(states);
        eap.setCategoriesIds(categories);
        eap.setRangeStart(rangeStart);
        eap.setRangeEnd(rangeEnd);
        eap.setFrom(from);
        eap.setSize(size);
        return eventService.getEventsByAdmin(eap);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(@PathVariable Long eventId,
                                   @RequestBody @Validated EventAdminUpdateDto eventAdminUpdateDto) {
        eventAdminUpdateDto.setId(eventId);
        return eventService.patchByAdmin(eventAdminUpdateDto);
    }
}
