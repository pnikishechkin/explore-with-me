package ru.practicum.ewm.main.compilation.dto;

import lombok.Data;
import ru.practicum.ewm.main.event.dto.EventShortDto;

import java.util.List;

@Data
public class CompilationDto {
    private Long id;
    private String title;
    private Boolean pinned;
    private List<EventShortDto> events;
}
