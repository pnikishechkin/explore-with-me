package ru.practicum.ewm.main.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.main.event.dto.EventShortDto;

import java.util.List;

@Data
public class NewCompilationDto {

    @NotNull
    @NotBlank
    @Length(max = 50)
    private String title;
    private Boolean pinned = false;
    private List<Long> events;
}
