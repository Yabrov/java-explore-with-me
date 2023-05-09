package ru.practicum.main.server.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.server.dto.event.EventFullDto;
import ru.practicum.main.server.model.enums.EventSort;
import ru.practicum.main.server.service.event.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;

@Validated
@RestController
@RequiredArgsConstructor
public class EventPublicController {

    private final EventService eventService;

    @GetMapping("/events")
    public Collection<EventFullDto> getEvents(@RequestParam(required = false) String text,
                                              @RequestParam(required = false) Collection<Long> categories,
                                              @RequestParam(required = false) Boolean paid,
                                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                              @RequestParam(required = false) LocalDateTime rangeStart,
                                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                              @RequestParam(required = false) LocalDateTime rangeEnd,
                                              @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                              @RequestParam(required = false) EventSort sort,
                                              @RequestParam(defaultValue = "0") Integer from,
                                              @RequestParam(defaultValue = "10") Integer size,
                                              HttpServletRequest request) {
        return eventService.getAllEventsPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEvent(@PathVariable Long id, HttpServletRequest request) {
        return eventService.getPublishedEventById(id);
    }

    @GetMapping("/events/locations")
    public Collection<EventFullDto> getEventsInsideZone(@RequestParam @NotNull Float longitude,
                                                        @RequestParam @NotNull Float latitude,
                                                        @RequestParam @NotNull Float radius,
                                                        HttpServletRequest request) {
        return eventService.findAllEventsInsideZone(longitude, latitude, radius);
    }
}
