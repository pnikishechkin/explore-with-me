package ru.practicum.ewm.statsclient;

import ru.practicum.ewm.statsdto.HitCreateDto;
import ru.practicum.ewm.statsdto.HitWithCountsDto;

public interface StatClient {
    void hit(HitCreateDto hitCreateDto);

    HitWithCountsDto getStat(ParamDto paramDto);
}
