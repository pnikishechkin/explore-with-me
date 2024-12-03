package ru.practicum.ewm.main.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByUserId(Long userId);

    List<Request> findByEventId(Long eventId);

    @Query(value = "SELECT count(*) FROM requests WHERE event_id = :eventId AND status = 2", nativeQuery = true)
    Integer getCountConfirmedRequestByEvent(Long eventId);
}
