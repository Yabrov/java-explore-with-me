package ru.practicum.main.server.repository.category;

import org.springframework.data.domain.Pageable;
import ru.practicum.main.server.model.entities.Category;

import java.util.Collection;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> findCategoryById(Long categoryId);

    Optional<Category> findCategoryByIdWithEvents(Long categoryId);

    Collection<Category> findAllCategories(Pageable pageable);

    Category saveCategory(Category category);

    void deleteCategory(Long categoryId);
}
