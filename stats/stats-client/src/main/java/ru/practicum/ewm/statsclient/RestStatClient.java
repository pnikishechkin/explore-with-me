package ru.practicum.ewm.statsclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.statsdto.HitCreateDto;
import ru.practicum.ewm.statsdto.HitWithCountsDto;

import java.time.LocalDateTime;
import java.util.List;

public class RestStatClient implements StatClient {
    final RestTemplate template;
    final String statUrl;

    public RestStatClient(RestTemplate template, @Value("${client.url}") String statUrl) {
        this.template = template;
        this.statUrl = statUrl;
    }

    @Override
    public void hit(HitCreateDto hitCreateDto) {
        //
    }

    @Override
    public HitWithCountsDto getStat(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        return new HitWithCountsDto();
    }

    public static void main(String[] args) {

    }

}
