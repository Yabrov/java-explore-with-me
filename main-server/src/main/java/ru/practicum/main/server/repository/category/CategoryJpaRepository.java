package ru.practicum.main.server.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.main.server.model.entities.Category;

import java.util.Optional;

@Repository
public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c JOIN FETCH c.events WHERE c.id = :categoryId")
    Optional<Category> findByIdWithEvents(Long categoryId);
}
