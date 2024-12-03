package ru.practicum.ewm.main.request.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestsStatusUpdateResultDto {
    List<RequestDto> confirmedRequests;
    List<RequestDto> rejectedRequests;
}
