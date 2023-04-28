package ru.practicum.main.server.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.server.dto.compilation.CompilationDto;
import ru.practicum.main.server.dto.compilation.NewCompilationDto;
import ru.practicum.main.server.dto.compilation.UpdateCompilationRequest;
import ru.practicum.main.server.service.compilation.CompilationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class CompilationAdminController {

    private final CompilationService compilationService;

    @PostMapping("/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {
        return compilationService.createCompilation(compilationDto);
    }

    @DeleteMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        compilationService.deleteCompilation(compId);
    }

    @PatchMapping("/compilations/{compId}")
    public CompilationDto updateCompilation(@RequestBody UpdateCompilationRequest updateRequest,
                                            @PathVariable Long compId) {
        return compilationService.updateCompilation(updateRequest, compId);
    }
}
