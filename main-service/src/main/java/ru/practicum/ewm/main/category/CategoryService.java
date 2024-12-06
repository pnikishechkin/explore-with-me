package ru.practicum.ewm.main.category;

import ru.practicum.ewm.main.category.dto.CategoryCreateDto;
import ru.practicum.ewm.main.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<Category> getAll(Integer from, Integer size);

    Category getById(Long catId);

    Category create(CategoryCreateDto categoryCreateDto);

    void delete(Long catId);

    Category update(CategoryDto categoryDto);
}
