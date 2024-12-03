package ru.practicum.ewm.main.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserCreateDto {
    @Email(message = "Некорректный формат электронной почты")
    @NotBlank(message = "Электронная почта должна быть задана")
    private String email;

    @NotBlank(message = "Имя пользователя должно быть задана")
    private String name;
}
