package ru.practicum.main.server.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.server.dto.event.EventFullDto;
import ru.practicum.main.server.dto.event.UpdateEventAdminRequest;
import ru.practicum.main.server.model.enums.EventState;
import ru.practicum.main.server.service.event.EventService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class EventAdminController {

    private final EventService eventService;

    @GetMapping("/events")
    public Collection<EventFullDto> getEvents(@RequestParam(required = false) Collection<Long> users,
                                              @RequestParam(required = false) Collection<EventState> states,
                                              @RequestParam(required = false) Collection<Long> categories,
                                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                              @RequestParam(required = false) LocalDateTime rangeStart,
                                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                              @RequestParam(required = false) LocalDateTime rangeEnd,
                                              @RequestParam(defaultValue = "0") Integer from,
                                              @RequestParam(defaultValue = "10") Integer size) {
        return eventService.getAllEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/events/{eventId}")
    public EventFullDto updateEvent(@RequestBody @Valid UpdateEventAdminRequest request,
                                    @PathVariable Long eventId) {
        return eventService.updateEvent(eventId, request);
    }
}
