package ru.practicum.main.server.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.server.dto.compilation.CompilationDto;
import ru.practicum.main.server.service.compilation.CompilationService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class CompilationPublicController {

    private final CompilationService compilationService;

    @GetMapping
    public Collection<CompilationDto> getAllCompilations(@RequestParam(required = false) Boolean pinned,
                                                         @RequestParam(defaultValue = "0") Integer from,
                                                         @RequestParam(defaultValue = "10") Integer size) {
        return compilationService.findAllCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        return compilationService.findCompilationById(compId);
    }
}
