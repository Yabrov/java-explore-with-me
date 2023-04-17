package ru.practicum.stats.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class ViewStatsDto {

    private String app;

    private String uri;

    private Integer hits;
}
