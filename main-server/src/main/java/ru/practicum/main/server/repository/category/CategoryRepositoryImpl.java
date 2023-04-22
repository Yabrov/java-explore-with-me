package ru.practicum.main.server.repository.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.main.server.model.entities.Category;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository jpaRepository;

    @Override
    public Optional<Category> findCategoryById(Long categoryId) {
        return jpaRepository.findById(categoryId);
    }

    @Override
    public Optional<Category> findCategoryByIdWithEvents(Long categoryId) {
        return jpaRepository.findByIdWithEvents(categoryId);
    }

    @Override
    public Collection<Category> findAllCategories(Pageable pageable) {
        return jpaRepository.findAll(pageable).getContent();
    }

    @Override
    public Category saveCategory(Category category) {
        return jpaRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        jpaRepository.deleteById(categoryId);
    }
}
