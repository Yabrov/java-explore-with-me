package ru.practicum.main.server.repository.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main.server.model.entities.Category;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Optional<Category> findCategoryById(Long categoryId) {
        return categoryJpaRepository.findById(categoryId);
    }

    @Override
    public Optional<Category> findCategoryByIdWithEvents(Long categoryId) {
        return categoryJpaRepository.findByIdWithEvents(categoryId);
    }

    @Override
    public Collection<Category> findAllCategories(Pageable pageable) {
        return categoryJpaRepository.findAll(pageable).getContent();
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryJpaRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryJpaRepository.deleteById(categoryId);
    }
}
