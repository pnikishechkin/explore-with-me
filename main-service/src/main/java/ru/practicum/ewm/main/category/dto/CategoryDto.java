package ru.practicum.ewm.main.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Data
@ToString
public class CategoryDto {
    private Long id;

    @NotNull
    @NotBlank
    @Length(max = 50)
    private String name;
}
