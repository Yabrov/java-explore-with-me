package ru.practicum.main.server.service.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.server.dto.compilation.CompilationDto;
import ru.practicum.main.server.dto.compilation.NewCompilationDto;
import ru.practicum.main.server.dto.compilation.UpdateCompilationRequest;
import ru.practicum.main.server.exception.CompilationNotFoundException;
import ru.practicum.main.server.exception.ConstraintViolationException;
import ru.practicum.main.server.model.entities.Compilation;
import ru.practicum.main.server.model.entities.Event;
import ru.practicum.main.server.repository.compilation.CompilationRepository;
import ru.practicum.main.server.repository.event.EventRepository;
import ru.practicum.main.server.utils.PageBuilder;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final Converter<NewCompilationDto, Compilation> newCompilationMapper;
    private final Converter<Compilation, CompilationDto> compilationMapper;
    private final PageBuilder pageBuilder;

    @Transactional
    @Override
    public CompilationDto createCompilation(NewCompilationDto compilationDto) {
        Compilation compilation = newCompilationMapper.convert(compilationDto);
        try {
            return compilationMapper.convert(compilationRepository.saveCompilation(compilation));
        } catch (Exception e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Transactional
    @Override
    public void deleteCompilation(Long compilationId) {
        Compilation compilation = compilationRepository.findCompilationById(compilationId)
                .orElseThrow(() -> new CompilationNotFoundException(compilationId));
        compilationRepository.deleteCompilation(compilation);
    }

    @Transactional
    @Override
    public CompilationDto updateCompilation(UpdateCompilationRequest updateRequest, Long compilationId) {
        Compilation compilation = compilationRepository.findCompilationById(compilationId)
                .orElseThrow(() -> new CompilationNotFoundException(compilationId));
        if (updateRequest.getEvents() != null) {
            Collection<Event> events = eventRepository.findAllEventsByIds(updateRequest.getEvents());
            for (Event event : events) {
                event.getCompilations().add(compilation);
            }
            compilation.getEvents().clear();
            compilation.getEvents().addAll(events);
        }
        if (updateRequest.getPinned() != null) {
            compilation.setPinned(updateRequest.getPinned());
        }
        if (updateRequest.getTitle() != null) {
            compilation.setTitle(updateRequest.getTitle());
        }
        try {
            return compilationMapper.convert(compilationRepository.saveCompilation(compilation));
        } catch (Exception e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<CompilationDto> findAllCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = pageBuilder.build(from, size, null);
        return compilationRepository
                .findAllCompilations(pinned, pageable)
                .stream()
                .map(compilationMapper::convert)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public CompilationDto findCompilationById(Long compilationId) {
        Compilation compilation = compilationRepository.findCompilationById(compilationId)
                .orElseThrow(() -> new CompilationNotFoundException(compilationId));
        return compilationMapper.convert(compilation);
    }
}
