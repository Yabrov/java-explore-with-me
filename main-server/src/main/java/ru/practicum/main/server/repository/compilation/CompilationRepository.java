package ru.practicum.main.server.repository.compilation;

import org.springframework.data.domain.Pageable;
import ru.practicum.main.server.model.entities.Compilation;

import java.util.Collection;
import java.util.Optional;

public interface CompilationRepository {

    Compilation saveCompilation(Compilation compilation);

    Optional<Compilation> findCompilationById(Long compilationId);

    void deleteCompilation(Compilation compilation);

    Collection<Compilation> findAllCompilations(Boolean pinned, Pageable pageable);
}
