package ru.practicum.stats.server.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class ViewStats {
    private String app;
    private String uri;
    private Integer hits;
}
