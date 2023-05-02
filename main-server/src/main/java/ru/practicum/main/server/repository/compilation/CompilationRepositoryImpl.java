package ru.practicum.main.server.repository.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main.server.model.entities.Compilation;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompilationRepositoryImpl implements CompilationRepository {

    private final CompilationJpaRepository compilationJpaRepository;

    @Override
    public Compilation saveCompilation(Compilation compilation) {
        return compilationJpaRepository.save(compilation);
    }

    @Override
    public Optional<Compilation> findCompilationById(Long compilationId) {
        return compilationJpaRepository.findById(compilationId);
    }

    @Override
    public void deleteCompilation(Compilation compilation) {
        compilationJpaRepository.delete(compilation);
    }

    @Override
    public Collection<Compilation> findAllCompilations(Boolean pinned, Pageable pageable) {
        return compilationJpaRepository.findAllCompilations(pinned, pageable).getContent();
    }
}
