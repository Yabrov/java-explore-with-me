package ru.practicum.main.server.repository.compilation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.server.model.entities.Compilation;

public interface CompilationJpaRepository extends JpaRepository<Compilation, Long> {

    @Query("SELECT c FROM Compilation c WHERE COALESCE(:pinned, null) = null OR c.pinned = :pinned")
    Page<Compilation> findAllCompilations(Boolean pinned, Pageable pageable);
}
