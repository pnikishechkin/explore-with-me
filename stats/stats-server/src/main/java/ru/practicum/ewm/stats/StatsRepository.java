package ru.practicum.ewm.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.stats.model.Hit;
import ru.practicum.ewm.stats.model.HitWithCounts;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Hit, Long> {

    // Заданы только даты и не уникальные IP
    // SELECT app, uri, COUNT(*) as hits FROM stats WHERE time between '2022-09-06' AND '2026-05-05' GROUP BY app, uri;
    @Query("SELECT s.app as app, s.uri as uri, COUNT(*) as hits FROM Hit s WHERE (s.time between ?1 AND " +
            "?2) GROUP BY s.app, s.uri ORDER BY hits DESC")
    List<HitWithCounts> findByDates(LocalDateTime start, LocalDateTime end);

    // Заданы только даты и уникальные IP
    // SELECT app, uri, COUNT(*) FROM () GROUP BY app, uri;
    @Query("SELECT d.app as app, d.uri as uri, COUNT(*) as hits FROM " +
            "(SELECT DISTINCT s.app as app, s.uri as uri, s.ip as ip FROM Hit s WHERE (s.time between ?1 and ?2)) AS " +
            "d " +
            "GROUP BY d.app, d.uri ORDER BY hits DESC")
    List<HitWithCounts> findByDatesUniqueIp(LocalDateTime start, LocalDateTime end);

    // Заданы даты и список uri, не уникальные IP
    // SELECT app, uri, COUNT(*) FROM stats WHERE (uri IN ('/events', '/times') AND (time between '2022-09-06' and
    //    '2026-05-05'))
    //GROUP BY app, uri;
    @Query("SELECT s.app as app, s.uri as uri, COUNT(*) as hits FROM Hit s WHERE (s.time between ?1 AND " +
            "?2) AND (s.uri IN (?3)) GROUP BY s.app, s.uri ORDER BY hits DESC")
    List<HitWithCounts> findByDatesAndUri(LocalDateTime start, LocalDateTime end, List<String> uris);

    // Заданы даты и список uri, уникальные IP
    //SELECT app, uri, COUNT(*) FROM (SELECT DISTINCT app, uri, ip FROM stats WHERE (time between '2022-09-06' and
    //    '2026-05-05')) WHERE (uri IN ('/events', '/times'))
    //GROUP BY app, uri;
    @Query("SELECT d.app as app, d.uri as uri, COUNT(*) as hits FROM " +
            "(SELECT DISTINCT s.app as app, s.uri as uri, s.ip as ip FROM Hit s WHERE (s.time between ?1 and ?2)) AS " +
            "d " +
            "WHERE (d.uri IN (?3)) GROUP BY d.app, d.uri ORDER BY hits DESC")
    List<HitWithCounts> findByDatesUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);
}
