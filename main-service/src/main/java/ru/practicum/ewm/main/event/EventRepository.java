package ru.practicum.ewm.main.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT * FROM events WHERE initiator_id = ?1 OFFSET ?2 LIMIT ?3", nativeQuery = true)
    List<Event> findByInitiatorIdFromSize(Long userId, Integer from, Integer size);

    Optional<Event> findByIdAndState(Long id, EventState state);

    @Query(value = "SELECT * FROM (SELECT *," +
            "       CASE" +
            "           WHEN (req-participant_limit <= 0) THEN false" +
            "           ELSE true" +
            "           END AS available" +
            "  FROM (SELECT e.*," +
            "             COUNT(r.id) AS req" +
            "      FROM events AS e" +
            "               LEFT JOIN requests AS r ON e.id = r.event_id" +
            "      WHERE (e.paid IN ?2) AND (e.date_event >= ?1) " +
            "      GROUP BY e.id" +
            "      ORDER BY date_event))" +
            " WHERE available IN ?3" +
            "      OFFSET ?4 LIMIT ?5", nativeQuery = true)
    List<Event> findByStartPaidAvailableFromSize(LocalDateTime rangeStart,
                                                 List<Boolean> paidValues,
                                                 List<Boolean> availables,
                                                 Integer from,
                                                 Integer size);

    @Query(value = "SELECT * FROM (SELECT *," +
            "       CASE" +
            "           WHEN (req-participant_limit <= 0) THEN false" +
            "           ELSE true" +
            "           END AS available" +
            "  FROM (SELECT e.*," +
            "             COUNT(r.id) AS req" +
            "      FROM events AS e" +
            "               LEFT JOIN requests AS r ON e.id = r.event_id" +
            "      WHERE (e.paid IN ?3) AND (e.date_event >= ?1) AND (e.date_event <= ?2)" +
            "      GROUP BY e.id" +
            "      ORDER BY date_event))" +
            " WHERE available IN ?4" +
            "      OFFSET ?5 LIMIT ?6", nativeQuery = true)
    List<Event> findByStartEndPaidAvailableFromSize(LocalDateTime rangeStart,
                                                    LocalDateTime rangeEnd,
                                                    List<Boolean> paidValues,
                                                    List<Boolean> availables,
                                                    Integer from,
                                                    Integer size);

    @Query(value = "SELECT * FROM (SELECT *," +
            "       CASE" +
            "           WHEN (req-participant_limit <= 0) THEN false" +
            "           ELSE true" +
            "           END AS available" +
            "  FROM (SELECT e.*," +
            "             COUNT(r.id) AS req" +
            "      FROM events AS e" +
            "               LEFT JOIN requests AS r ON e.id = r.event_id" +
            "      WHERE (e.paid IN ?3) AND (e.date_event >= ?1) AND (e.category_id IN ?2)" +
            "      GROUP BY e.id" +
            "      ORDER BY date_event))" +
            " WHERE available IN ?4" +
            "      OFFSET ?5 LIMIT ?6", nativeQuery = true)
    List<Event> findByCategoriesStartPaidAvailableFromSize(LocalDateTime rangeStart,
                                                           List<Long> categories,
                                                           List<Boolean> paidValues,
                                                           List<Boolean> availables,
                                                           Integer from,
                                                           Integer size);

    @Query(value = "SELECT * FROM (SELECT *," +
            "       CASE" +
            "           WHEN (req-participant_limit <= 0) THEN false" +
            "           ELSE true" +
            "           END AS available" +
            "  FROM (SELECT e.*," +
            "             COUNT(r.id) AS req" +
            "      FROM events AS e" +
            "               LEFT JOIN requests AS r ON e.id = r.event_id" +
            "      WHERE (e.paid IN ?4) AND (e.date_event >= ?1)  AND (e.date_event <= ?2) AND (e.category_id IN ?3)" +
            "      GROUP BY e.id" +
            "      ORDER BY date_event))" +
            " WHERE available IN ?5" +
            "      OFFSET ?6 LIMIT ?7", nativeQuery = true)
    List<Event> findByStartEndCategoriesPaidAvailableFromSize(LocalDateTime rangeStart,
                                                              LocalDateTime rangeEnd,
                                                              List<Long> categories,
                                                              List<Boolean> paidValues,
                                                              List<Boolean> availables,
                                                              Integer from,
                                                              Integer size);

    @Query(value = "SELECT * FROM (SELECT *," +
            "       CASE" +
            "           WHEN (req-participant_limit <= 0) THEN false" +
            "           ELSE true" +
            "           END AS available" +
            "  FROM (SELECT e.*," +
            "             COUNT(r.id) AS req" +
            "      FROM events AS e" +
            "               LEFT JOIN requests AS r ON e.id = r.event_id" +
            "      WHERE (upper(e.description) LIKE upper(concat('%', ?1, '%')) " +
            "OR upper(e.annotation) LIKE upper(concat('%', ?1, '%'))) " +
            "AND (e.paid IN ?3) AND (e.date_event >= ?2) " +
            "      GROUP BY e.id" +
            "      ORDER BY date_event))" +
            " WHERE available IN ?4" +
            "      OFFSET ?5 LIMIT ?6", nativeQuery = true)
    List<Event> findByTextStartPaidAvailableFromSize(String text,
                                                     LocalDateTime rangeStart,
                                                     List<Boolean> paidValues,
                                                     List<Boolean> availables,
                                                     Integer from,
                                                     Integer size);

    @Query(value = "SELECT * FROM (SELECT *," +
            "       CASE" +
            "           WHEN (req-participant_limit <= 0) THEN false" +
            "           ELSE true" +
            "           END AS available" +
            "  FROM (SELECT e.*," +
            "             COUNT(r.id) AS req" +
            "      FROM events AS e" +
            "               LEFT JOIN requests AS r ON e.id = r.event_id" +
            "      WHERE (upper(e.description) LIKE upper(concat('%', ?1, '%')) " +
            "OR upper(e.annotation) LIKE upper(concat('%', ?1, '%'))) " +
            "AND (e.paid IN ?4) AND (e.date_event >= ?2) AND (e.date_event <= ?3)" +
            "      GROUP BY e.id" +
            "      ORDER BY date_event))" +
            " WHERE available IN ?5" +
            "      OFFSET ?6 LIMIT ?7", nativeQuery = true)
    List<Event> findByTextStartEndPaidAvailableFromSize(String text,
                                                        LocalDateTime rangeStart,
                                                        LocalDateTime rangeEnd,
                                                        List<Boolean> paidValues,
                                                        List<Boolean> availables,
                                                        Integer from,
                                                        Integer size);

    @Query(value = "SELECT * FROM (SELECT *," +
            "       CASE" +
            "           WHEN (req-participant_limit <= 0) THEN false" +
            "           ELSE true" +
            "           END AS available" +
            "  FROM (SELECT e.*," +
            "             COUNT(r.id) AS req" +
            "      FROM events AS e" +
            "               LEFT JOIN requests AS r ON e.id = r.event_id" +
            "      WHERE (upper(e.description) LIKE upper(concat('%', ?1, '%')) " +
            "OR upper(e.annotation) LIKE upper(concat('%', ?1, '%'))) " +
            "AND (e.paid IN ?4) AND (e.date_event >= ?2) AND (e.category_id IN ?3)" +
            "      GROUP BY e.id" +
            "      ORDER BY date_event))" +
            " WHERE available IN ?5" +
            "      OFFSET ?6 LIMIT ?7", nativeQuery = true)
    List<Event> findByTextStartCategoryPaidAvailableFromSize(String text,
                                                             LocalDateTime rangeStart,
                                                             List<Long> categories,
                                                             List<Boolean> paidValues,
                                                             List<Boolean> availables,
                                                             Integer from,
                                                             Integer size);

    @Query(value = "SELECT * FROM (SELECT *," +
            "       CASE" +
            "           WHEN (req-participant_limit <= 0) THEN false" +
            "           ELSE true" +
            "           END AS available" +
            "  FROM (SELECT e.*," +
            "             COUNT(r.id) AS req" +
            "      FROM events AS e" +
            "               LEFT JOIN requests AS r ON e.id = r.event_id" +
            "      WHERE (upper(e.description) LIKE upper(concat('%', ?1, '%')) " +
            "OR upper(e.annotation) LIKE upper(concat('%', ?1, '%'))) " +
            "AND (e.paid IN ?5) AND (e.date_event >= ?2) AND (e.date_event <= ?3) AND (e.category_id IN ?4)" +
            "      GROUP BY e.id" +
            "      ORDER BY date_event))" +
            " WHERE available IN ?6" +
            "      OFFSET ?7 LIMIT ?8", nativeQuery = true)
    List<Event> findByTextStartEndCategoryPaidAvailableFromSize(String text,
                                                                LocalDateTime rangeStart,
                                                                LocalDateTime rangeEnd,
                                                                List<Long> categories,
                                                                List<Boolean> paidValues,
                                                                List<Boolean> availables,
                                                                Integer from,
                                                                Integer size);

//    @Query(value = "SELECT * FROM (SELECT *," +
//            "       CASE" +
//            "           WHEN (req-participant_limit <= 0) THEN false" +
//            "           ELSE true" +
//            "           END AS available" +
//            "  FROM (SELECT e.*," +
//            "             COUNT(r.id) AS req" +
//            "      FROM events AS e" +
//            "               LEFT JOIN requests AS r ON e.id = r.event_id" +
//            "      WHERE " +
//            "(CAST(:text AS text) IS NULL OR " +
//            "(upper(e.description) LIKE upper(concat('%', :text, '%')) OR " +
//            "upper(e.annotation) LIKE upper(concat('%', :text, '%')))) AND " +
//            "(CAST(:paid AS boolean) IS NULL OR e.paid = :paid) AND " +
//            "(CAST(:start AS timestamp) IS NULL OR e.date_event >= :start) AND " +
//            "(CAST(:end AS timestamp) IS NULL OR e.date_event <= :end) AND " +
//            "(CAST(:categories AS text) IS NULL OR e.category_id IN :categories) " +
//            "      GROUP BY e.id" +
//            "      ORDER BY e.date_event)" +
//            " WHERE (CAST(:availables AS boolean) IS NULL OR available IN :availables))" +
//            "      OFFSET :from LIMIT :size", nativeQuery = true)


    @Query(value = "SELECT * FROM (SELECT *," +
            "       CASE" +
            "           WHEN (req-participant_limit <= 0) THEN false" +
            "           ELSE true" +
            "           END AS available" +
            "  FROM (SELECT e.*," +
            "             COUNT(r.id) AS req" +
            "      FROM events AS e" +
            "               LEFT JOIN requests AS r ON e.id = r.event_id" +
            "      WHERE " +
            "(CAST(:text AS text) IS NULL OR " +
            "(upper(e.description) LIKE upper(concat('%', :text, '%')) OR " +
            "upper(e.annotation) LIKE upper(concat('%', :text, '%')))) AND " +
            "(CAST(:paid AS boolean) IS NULL OR e.paid = :paid) AND " +
            "(CAST(:categories AS text) IS NULL OR e.category_id IN :categories) AND " +
            "(CAST(:start AS timestamp) IS NULL OR e.date_event >= :start) AND " +
            "(CAST(:end AS timestamp) IS NULL OR e.date_event <= :end) " +
            "      GROUP BY e.id" +
            "      ORDER BY date_event)) " +
            "WHERE (CAST(:available AS boolean) IS NULL OR available = :available) " +
            "      OFFSET :from LIMIT :size", nativeQuery = true)
    List<Event> findPublic(String text,
                           LocalDateTime start,
                          LocalDateTime end,
                           List<Long> categories,
                          Boolean paid,
                           Boolean available,
                          Integer from,
                           Integer size);

    @Query(value = "SELECT * FROM (SELECT *," +
            "       CASE" +
            "           WHEN (req-participant_limit <= 0) THEN false" +
            "           ELSE true" +
            "           END AS available" +
            "  FROM (SELECT e.*," +
            "             COUNT(r.id) AS req" +
            "      FROM events AS e" +
            "               LEFT JOIN requests AS r ON e.id = r.event_id" +
            "      WHERE " +
            "(CAST(:text AS text) IS NULL OR " +
            "(upper(e.description) LIKE upper(concat('%', :text, '%')) OR " +
            "upper(e.annotation) LIKE upper(concat('%', :text, '%')))) AND " +
            "(CAST(:paid AS boolean) IS NULL OR e.paid = :paid) AND " +
            "(CAST(:categories AS text) IS NULL OR e.category_id IN :categories) AND " +
            "(CAST(:start AS timestamp) IS NULL OR e.date_event >= :start) AND " +
            "(CAST(:end AS timestamp) IS NULL OR e.date_event <= :end) " +
            "      GROUP BY e.id)) " +
            "WHERE (CAST(:available AS boolean) IS NULL OR available = :available) " +
            "      OFFSET :from LIMIT :size", nativeQuery = true)
    List<Event> findPublicNoSort(String text,
                           LocalDateTime start,
                           LocalDateTime end,
                           List<Long> categories,
                           Boolean paid,
                           Boolean available,
                           Integer from,
                           Integer size);

    @Query(value = "SELECT * FROM events WHERE " +
            "(CAST(:users AS text) IS NULL OR initiator_id IN :users) AND " +
            "(CAST(:states AS text) IS NULL OR state IN :states) AND " +
            "(CAST(:categories AS text) IS NULL OR category_id IN :categories) AND " +
            "(CAST(:start AS timestamp) IS NULL OR date_event >= :start) AND " +
            "(CAST(:end AS timestamp) IS NULL OR date_event <= :end) " +
            "OFFSET (:from) LIMIT (:size)", nativeQuery = true)
    List<Event> findByAdmin(List<Long> users, List<Integer> states, List<Long> categories,
                            LocalDateTime start, LocalDateTime end,
                            Integer from, Integer size);

}
