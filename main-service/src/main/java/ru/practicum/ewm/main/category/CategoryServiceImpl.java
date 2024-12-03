package ru.practicum.ewm.main.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.main.category.dto.CategoryCreateDto;
import ru.practicum.ewm.main.category.dto.CategoryDto;
import ru.practicum.ewm.main.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll(Integer from, Integer size) {

        // return categoryRepository.findAll(PageRequest.of(from, size)).stream().toList();
        return categoryRepository.findAllLimitOffset(from, size);
    }

    @Override
    public Category getById(Long catId) {
        return categoryRepository.findById(catId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Категории с заданным идентификатором не существует"));
    }

    @Override
    public Category create(CategoryCreateDto categoryCreateDto) {
        Category category = CategoryMapper.toEntity(categoryCreateDto);
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long catId) {
        categoryRepository.findById(catId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Категории с заданным идентификатором не существует"));

        categoryRepository.deleteById(catId);
    }

    @Override
    public Category update(CategoryDto categoryDto) {

        categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new NotFoundException(
                "Ошибка! Категории с заданным идентификатором не существует"));

        Category category = CategoryMapper.toEntity(categoryDto);
        return categoryRepository.save(category);
    }
}
