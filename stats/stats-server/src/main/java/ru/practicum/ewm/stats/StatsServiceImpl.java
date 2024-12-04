package ru.practicum.ewm.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.stats.model.Hit;
import ru.practicum.ewm.stats.model.HitWithCounts;
import ru.practicum.ewm.statsdto.HitCreateDto;
import ru.practicum.ewm.statsdto.HitWithCountsDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    public Hit createHit(HitCreateDto hitCreateDto) {
        log.info("StatsServiceImpl: сохранение нового запроса в сервисе статистики: {}", hitCreateDto);
        return statsRepository.save(HitMapper.toEntity(hitCreateDto));
    }

    @Override
    public List<HitWithCountsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

        List<HitWithCounts> res = null;

        if (uris == null) {
            if (!unique) {
                res = statsRepository.findByDates(start, end);
            } else {
                res = statsRepository.findByDatesUniqueIp(start, end);
            }
        } else {
            if (!unique) {
                res = statsRepository.findByDatesAndUri(start, end, uris);
            } else {
                res = statsRepository.findByDatesUniqueIp(start, end, uris);
            }
        }

        List<HitWithCountsDto> result = res.stream().map(HitMapper::toHitWithCountsDto).toList();
        return result;
    }
}
