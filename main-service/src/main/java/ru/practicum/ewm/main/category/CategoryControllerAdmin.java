package ru.practicum.ewm.main.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.category.dto.CategoryCreateDto;
import ru.practicum.ewm.main.category.dto.CategoryDto;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CategoryControllerAdmin {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody @Validated CategoryCreateDto categoryCreateDto) {
        return categoryService.create(categoryCreateDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long catId) {
        categoryService.delete(catId);
    }

    @PatchMapping("/{catId}")
    public Category updateCategory(@PathVariable Long catId, @RequestBody @Validated CategoryDto categoryDto) {
        categoryDto.setId(catId);
        return categoryService.update(categoryDto);
    }
}
