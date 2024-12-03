package ru.practicum.ewm.main.request;

import ru.practicum.ewm.main.request.dto.RequestDto;

import java.util.List;

public interface RequestService {
    List<RequestDto> getRequestsByUser(Long userId);

    RequestDto post(Long userId, Long eventId);

    RequestDto cancel(Long userId, Long requestId);
}
