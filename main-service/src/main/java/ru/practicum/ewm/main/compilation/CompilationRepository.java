package ru.practicum.ewm.main.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    @Query(value="SELECT * FROM compilations " +
            "OFFSET ?1 LIMIT ?2", nativeQuery = true)
    List<Compilation> findByFromSize(Integer from, Integer size);

    @Query(value="SELECT * FROM compilations " +
            "WHERE pinned=?1 OFFSET ?2 LIMIT ?3", nativeQuery = true)
    List<Compilation> findByPinnedFromSize(Boolean pinned, Integer from, Integer size);
}
