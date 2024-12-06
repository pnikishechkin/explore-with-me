package ru.practicum.ewm.main.category;

import ru.practicum.ewm.main.category.dto.CategoryCreateDto;
import ru.practicum.ewm.main.category.dto.CategoryDto;

public class CategoryMapper {
    public static Category toEntity(CategoryCreateDto categoryCreateDto) {
        Category category = new Category();
        category.setName(categoryCreateDto.getName());
        return category;
    }

    public static Category toEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }

    public static CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }
}
