package ru.practicum.main.server.controller.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.server.dto.event.*;
import ru.practicum.main.server.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.main.server.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.main.server.dto.request.ParticipationRequestDto;
import ru.practicum.main.server.service.event.EventService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class EventPrivateController {

    private final EventService eventService;

    @GetMapping("/{userId}/events")
    public Collection<EventShortDto> getUsersEvents(@PathVariable Long userId,
                                                    @RequestParam(defaultValue = "0") Integer from,
                                                    @RequestParam(defaultValue = "10") Integer size) {
        return eventService.getAllUsersEvents(userId, from, size);
    }

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable Long userId, @RequestBody @Valid NewEventDto eventDto) {
        return eventService.createEvent(userId, eventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getUsersEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        return eventService.getUsersEventById(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto updateEvent(@RequestBody @Valid UpdateEventUserRequest request,
                                    @PathVariable Long userId,
                                    @PathVariable Long eventId) {
        return eventService.userUpdateEvent(userId, eventId, request);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public Collection<ParticipationRequestDto> getAllUsersEventRequests(@PathVariable Long userId,
                                                                        @PathVariable Long eventId) {
        return eventService.getUsersEventRequests(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult updateUserEventRequests(@RequestBody @Valid EventRequestStatusUpdateRequest request,
                                                                  @PathVariable Long userId,
                                                                  @PathVariable Long eventId) {
        return eventService.updateUserEventRequests(request, userId, eventId);
    }
}
