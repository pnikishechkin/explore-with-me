package ru.practicum.ewm.stats;

import ru.practicum.ewm.stats.model.Hit;
import ru.practicum.ewm.statsdto.HitCreateDto;
import ru.practicum.ewm.statsdto.HitWithCountsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    Hit createHit(HitCreateDto hitCreateDto);

    List<HitWithCountsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
