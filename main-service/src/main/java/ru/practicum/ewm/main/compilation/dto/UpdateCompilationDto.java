package ru.practicum.ewm.main.compilation.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class UpdateCompilationDto {
    private Long id;

    @Length(max = 50)
    private String title;
    private Boolean pinned;
    private List<Long> events;
}
