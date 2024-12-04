package ru.practicum.ewm.statsclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.ewm.statsdto.HitCreateDto;
import ru.practicum.ewm.statsdto.HitWithCountsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Component
@Slf4j
public class RestStatClient implements StatClient {
    private final RestClient client;
    private final String statUrl;

    public RestStatClient(@Value("${client.url}") String statUrl) {
        this.client = RestClient.create();
        this.statUrl = statUrl;
    }

    @Override
    public void createHit(HitCreateDto hitCreateDto) {
        try {
            client.post()
                    .uri(statUrl + "/hit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(hitCreateDto)
                    .retrieve()
                    .toBodilessEntity();
            log.info("Сохранение нового запроса в сервисе статистики...");
        } catch (ResourceAccessException ignored) {
            log.warn("Ошибка сохранения запроса в сервисе статистики");
        }
    }

    @Override
    public List<HitWithCountsDto> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        String startString = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String endString = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String urisString = uris.stream().collect(Collectors.joining(",")); //String.join(",", uris);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path("/stats")
                .query("start={keyword}")
                .query("end={keyword}")
                .query("uris={keyword}")
                .query("unique={keyword}")
                .buildAndExpand(startString, endString, urisString, unique);

        log.info("Запрос на получение статистики, параметры: {} {} {}", startString, endString, urisString);

        try {
            log.info("getStat UriComponents: {}", uriComponents.toUriString());
            return client.get().uri(statUrl + uriComponents.toUriString()).retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (ResourceAccessException e) {
            log.info("getStat exception: {}", e.getMessage());
            return List.of();
        }

    }

    public static void main(String[] args) {

    }

}
