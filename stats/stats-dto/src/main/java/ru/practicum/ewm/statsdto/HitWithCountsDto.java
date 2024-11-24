package ru.practicum.ewm.statsdto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class HitWithCountsDto {
    private String app;
    private String uri;
    private Integer hits;
}
