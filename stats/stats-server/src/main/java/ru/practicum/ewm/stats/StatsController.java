package ru.practicum.ewm.stats;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.stats.model.Hit;
import ru.practicum.ewm.statsdto.HitCreateDto;
import ru.practicum.ewm.statsdto.HitWithCountsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public Hit createHit(@RequestBody HitCreateDto hitCreateDto) {
        return statsService.createHit(hitCreateDto);
    }

    @GetMapping("/stats")
    public List<HitWithCountsDto> getStat(@RequestParam @NotNull String start,
                                          @RequestParam @NotNull String end,
                                          @RequestParam(required = false) List<String> uris,
                                          @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return statsService.getStats(startDate, endDate, uris, unique);
    }
}
