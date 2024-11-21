package ru.practicum.ewm.stats;

import ru.practicum.ewm.stats.model.Hit;
import ru.practicum.ewm.stats.model.HitWithCounts;
import ru.practicum.ewm.statsdto.HitCreateDto;
import ru.practicum.ewm.statsdto.HitWithCountsDto;

public class HitMapper {
    public static Hit toEntity(HitCreateDto hitCreateDto) {
        Hit entity = new Hit();
        entity.setIp(hitCreateDto.getIp());
        entity.setApp(hitCreateDto.getApp());
        entity.setUri(hitCreateDto.getUri());
        entity.setTime(hitCreateDto.getTimestamp());
        return entity;
    }

    public static HitWithCountsDto toHitWithCountsDto(HitWithCounts hitWithCounts) {
        HitWithCountsDto dto = new HitWithCountsDto();
        dto.setApp(hitWithCounts.getApp());
        dto.setUri(hitWithCounts.getUri());
        dto.setHits(hitWithCounts.getHits());
        return dto;
    }
}
