package ru.practicum.ewm.main.request.dto;

import lombok.Data;
import ru.practicum.ewm.main.request.RequestStatus;

import java.util.List;

@Data
public class RequestsStatusUpdateDto {
    List<Long> requestIds;
    RequestStatus status;
}
