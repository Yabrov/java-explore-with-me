package ru.practicum.main.server.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageBuilder {

    public Pageable build(Integer from, Integer size, Sort sort) {
        return sort == null
                ? PageRequest.of(from / size, size)
                : PageRequest.of(from / size, size, sort);
    }
}
