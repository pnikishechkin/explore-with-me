package ru.practicum.ewm.main.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.event.dto.EventCreateDto;
import ru.practicum.ewm.main.event.dto.EventFullDto;
import ru.practicum.ewm.main.event.dto.EventShortDto;
import ru.practicum.ewm.main.event.dto.EventUserUpdateDto;
import ru.practicum.ewm.main.request.dto.RequestDto;
import ru.practicum.ewm.main.request.dto.RequestsStatusUpdateDto;
import ru.practicum.ewm.main.request.dto.RequestsStatusUpdateResultDto;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventControllerPrivate {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEvents(@PathVariable Long userId,
                                         @RequestParam(defaultValue = "0") Integer from,
                                         @RequestParam(defaultValue = "10") Integer size) {
        return eventService.getEventsByUser(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto postEvent(@PathVariable Long userId,
                                  @RequestBody @Validated EventCreateDto eventCreateDto) {
        eventCreateDto.setInitiatorId(userId);
        return eventService.post(eventCreateDto);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(@PathVariable Long userId,
                                   @PathVariable Long eventId,
                                   @RequestBody @Validated EventUserUpdateDto eventUserUpdateDto) {
        eventUserUpdateDto.setId(eventId);
        return eventService.patch(userId, eventUserUpdateDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable Long userId,
                                 @PathVariable Long eventId) {
        return eventService.getEventByUser(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getRequestsByEvent(@PathVariable Long userId,
                                               @PathVariable Long eventId) {
        return eventService.getRequestsByEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public RequestsStatusUpdateResultDto patchRequests(@PathVariable Long userId,
                                                       @PathVariable Long eventId,
                                                       @RequestBody @Validated RequestsStatusUpdateDto requestsStatusUpdateDto) {
        return eventService.patchRequestsStatus(userId, eventId, requestsStatusUpdateDto);
    }
}
