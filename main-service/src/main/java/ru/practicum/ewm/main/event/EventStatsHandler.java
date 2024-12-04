package ru.practicum.ewm.main.event;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.statsclient.StatClient;
import ru.practicum.ewm.statsdto.HitCreateDto;
import ru.practicum.ewm.statsdto.HitWithCountsDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventStatsHandler {
    private final StatClient statsClient;

    public void addHit(HttpServletRequest request) {
        HitCreateDto hitCreateDto = new HitCreateDto();

        hitCreateDto.setUri(request.getRequestURI());
        hitCreateDto.setIp(request.getRemoteAddr());
        hitCreateDto.setTimestamp(LocalDateTime.now());
        hitCreateDto.setApp("Explore with me");

        log.info("В сервис статистики сохранен новый запрос: {}", hitCreateDto);

        statsClient.createHit(hitCreateDto);
    }

    public Integer getCountHitsToEvent(Long eventId) {

        List<HitWithCountsDto> hits = statsClient.getStat(
                LocalDateTime.now().minusYears(1000),
                LocalDateTime.now().plusYears(1000),
                List.of(getUri(eventId)),
                true);

        Integer res = hits.isEmpty() ? 0 : hits.getFirst().getHits();

        log.info("Количество просмотров события с идентификатором {}: {}", eventId, res);

        return res;
    }

    private String getUri(Long eventId) {
        return "/events/" + eventId.toString();
    }

    public Map<Long, Integer> getCountHitsToEvents(List<Long> eventIds) {
        Map<Long, Integer> res = new HashMap<>();

        List<String> uris = eventIds.stream().map(this::getUri).toList();

        List<HitWithCountsDto> hits = statsClient.getStat(
                LocalDateTime.now().minusYears(1000),
                LocalDateTime.now().plusYears(1000),
                uris,
                true);

        eventIds.stream().forEach(e -> {
            res.put(e, hits.stream().filter(hit -> hit.getUri().equals(getUri(e))).findFirst().isPresent() ?
                    hits.stream().filter(hit -> hit.getUri().equals(getUri(e))).findFirst().get().getHits() : 0);
        });

        return res;

    }
}
