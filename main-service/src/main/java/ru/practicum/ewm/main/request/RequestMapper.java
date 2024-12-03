package ru.practicum.ewm.main.request;

import ru.practicum.ewm.main.request.dto.RequestDto;

public class RequestMapper {

    public static RequestDto toDto(Request request) {
        RequestDto dto = new RequestDto();
        dto.setId(request.getId());
        dto.setRequester(request.getUser().getId());
        dto.setCreated(request.getDate());
        dto.setEvent(request.getEvent().getId());
        dto.setStatus(request.getStatus());
        return dto;
    }
}
