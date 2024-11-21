package ru.practicum.ewm.statsclient;

import ru.practicum.ewm.statsdto.HitCreateDto;
import ru.practicum.ewm.statsdto.HitWithCountsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatClient {
    void hit(HitCreateDto hitCreateDto);

    HitWithCountsDto getStat(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
