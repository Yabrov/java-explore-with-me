package ru.practicum.main.server.service.compilation;

import ru.practicum.main.server.dto.compilation.CompilationDto;
import ru.practicum.main.server.dto.compilation.NewCompilationDto;
import ru.practicum.main.server.dto.compilation.UpdateCompilationRequest;

import java.util.Collection;

public interface CompilationService {

    CompilationDto createCompilation(NewCompilationDto compilationDto);

    void deleteCompilation(Long compilationId);

    CompilationDto updateCompilation(UpdateCompilationRequest updateRequest, Long compilationId);

    Collection<CompilationDto> findAllCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto findCompilationById(Long compilationId);
}
