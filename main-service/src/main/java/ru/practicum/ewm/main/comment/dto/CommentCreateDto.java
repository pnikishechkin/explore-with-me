package ru.practicum.ewm.main.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentCreateDto {
    @NotBlank
    private String text;
    private Long userId;
    private Long eventId;
}
