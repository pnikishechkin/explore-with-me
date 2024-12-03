package ru.practicum.ewm.main.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main.category.Category;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value="SELECT * FROM users WHERE id IN ?1 OFFSET ?2 LIMIT ?3", nativeQuery = true)
    List<User> findByIdsLimitOffset(List<Long> ids, Integer offset, Integer limit);

    @Query(value="SELECT * FROM users OFFSET ?1 LIMIT ?2", nativeQuery = true)
    List<User> findLimitOffset(Integer offset, Integer limit);

}
