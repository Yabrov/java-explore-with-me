package ru.practicum.main.server.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.server.dto.location.AllowedLocationDto;
import ru.practicum.main.server.dto.location.NewLocationRequest;
import ru.practicum.main.server.service.location.LocationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class LocationAdminController {

    private final LocationService locationService;

    @PostMapping("/locations")
    @ResponseStatus(HttpStatus.CREATED)
    public AllowedLocationDto addAllowedLocation(@RequestBody @Valid NewLocationRequest locationRequest) {
        return locationService.addAllowedLocation(locationRequest);
    }

    @PatchMapping("/locations/{id}")
    public AllowedLocationDto updateAllowedLocation(@PathVariable Long id,
                                                    @RequestBody AllowedLocationDto dto) {
        return locationService.updateAllowedLocation(id, dto);
    }

    @DeleteMapping("/locations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllowedLocation(@PathVariable Long id) {
        locationService.deleteAllowedLocation(id);
    }
}
