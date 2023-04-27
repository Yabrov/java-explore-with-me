package ru.practicum.main.server.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.server.dto.category.CategoryDto;
import ru.practicum.main.server.service.category.CategoryService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class CategoryPublicController {

    private final CategoryService categoryService;

    @GetMapping("/categories/{catId}")
    public CategoryDto getCategory(@PathVariable Long catId) {
        return categoryService.getCategory(catId);
    }

    @GetMapping("/categories")
    public Collection<CategoryDto> getAllCategories(@RequestParam(defaultValue = "0") Integer from,
                                                    @RequestParam(defaultValue = "10") Integer size) {
        return categoryService.getAllCategories(from, size);
    }
}
