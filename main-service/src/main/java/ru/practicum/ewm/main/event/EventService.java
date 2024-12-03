package ru.practicum.ewm.main.event;

import ru.practicum.ewm.main.event.dto.*;
import ru.practicum.ewm.main.request.dto.RequestDto;
import ru.practicum.ewm.main.request.dto.RequestsStatusUpdateDto;
import ru.practicum.ewm.main.request.dto.RequestsStatusUpdateResultDto;

import java.util.List;

public interface EventService {

    EventFullDto post(EventCreateDto eventCreateDto);

    List<EventShortDto> getEventsByUser(Long userId, Integer from, Integer size);

    EventFullDto getEventByUser(Long userId, Long eventId);

    EventFullDto patch(Long userId, EventUserUpdateDto eventUserUpdateDto);

    List<EventShortDto> getEventsByParams(EventParams eventParams);

    EventFullDto patchByAdmin(EventAdminUpdateDto eventAdminUpdateDto);

    EventFullDto getPublicEventById(Long eventId);

    List<RequestDto> getRequestsByEvent(Long userId, Long eventId);

    RequestsStatusUpdateResultDto patchRequestsStatus(Long userId, Long eventId,
                                                      RequestsStatusUpdateDto requestsStatusUpdateDto);

    List<EventFullDto> getEventsByAdmin(EventAdminParams eap);
}
