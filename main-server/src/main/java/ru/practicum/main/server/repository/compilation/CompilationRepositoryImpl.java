package ru.practicum.main.server.repository.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.main.server.model.entities.Compilation;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CompilationRepositoryImpl implements CompilationRepository {

    private final CompilationJpaRepository jpaRepository;

    @Override
    public Compilation saveCompilation(Compilation compilation) {
        return jpaRepository.save(compilation);
    }

    @Override
    public Optional<Compilation> findCompilationById(Long compilationId) {
        return jpaRepository.findById(compilationId);
    }

    @Override
    public void deleteCompilation(Compilation compilation) {
        jpaRepository.delete(compilation);
    }

    @Override
    public Collection<Compilation> findAllCompilations(Boolean pinned, Pageable pageable) {
        return jpaRepository.findAllCompilations(pinned, pageable);
    }
}
