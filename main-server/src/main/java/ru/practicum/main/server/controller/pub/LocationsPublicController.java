package ru.practicum.main.server.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.server.dto.location.AllowedLocationDto;
import ru.practicum.main.server.service.location.LocationService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/locations/allowed")
public class LocationsPublicController {

    private final LocationService locationService;

    @GetMapping
    public Collection<AllowedLocationDto> getAllAllowedLocations() {
        return locationService.getAllAllowedLocations();
    }

    @GetMapping("/{id}")
    public AllowedLocationDto getAllowedLocation(@PathVariable Long id) {
        return locationService.getAllowedLocationById(id);
    }
}
