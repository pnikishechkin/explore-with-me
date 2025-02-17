package ru.practicum.ewm.main.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByUserId(Long userId);

    List<Request> findByEventId(Long eventId);

    @Query(value = "SELECT count(*) FROM requests WHERE event_id = :eventId AND status = 1", nativeQuery = true)
    Integer getCountConfirmedRequestByEvent(Long eventId);

    @Query(value = "SELECT e.id AS event_id, COUNT(r.id) AS count FROM events AS e LEFT JOIN " +
            "    (SELECT * FROM requests WHERE status=1) AS r ON e.id = r.event_id " +
            "WHERE e.id IN :eventIds " +
            "GROUP BY e.id", nativeQuery = true)
    List<EventCountConfirmedRequests> getCountRequests(List<Long> eventIds);

    Optional<Request> findByUserIdAndEventId(Long userId, Long eventId);
}
