package ru.practicum.ewm.statsclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.ewm.statsdto.HitCreateDto;
import ru.practicum.ewm.statsdto.HitWithCountsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        } catch (ResourceAccessException ignored) {
        }
    }

    @Override
    public List<HitWithCountsDto> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        String startString = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String endString = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String urisString = String.join(",", uris);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost:9090")
                .path("/stats")
                .query("start={keyword}")
                .query("end={keyword}")
                .query("uris={keyword}")
                .query("unique={keyword}")
                .buildAndExpand(startString, endString, urisString, unique);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return client.get()
                    .uri(uriComponents.toUriString())
                    .exchange((request, response) -> {
                        try {
                            if (response.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
                                return objectMapper.readValue(response.getBody(), new TypeReference<>() {
                                });
                            } else {
                                throw new ResourceAccessException("StatServer error");
                            }
                        } catch (ResourceAccessException e) {
                            return List.of();
                        }
                    });
        } catch (ResourceAccessException e) {
            return List.of();
        }
    }

    public static void main(String[] args) {

    }

}
