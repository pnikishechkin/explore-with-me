package ru.practicum.ewm.main.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM categories OFFSET ?1 LIMIT ?2", nativeQuery = true)
    List<Category> findAllLimitOffset(Integer offset, Integer limit);

}
