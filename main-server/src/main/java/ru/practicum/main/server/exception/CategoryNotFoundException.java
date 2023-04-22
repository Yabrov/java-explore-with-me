package ru.practicum.main.server.exception;

public class CategoryNotFoundException extends RuntimeException {

    private static final String ERROR_MES = "Category with id=%s was not found";

    public CategoryNotFoundException(Long categoryId) {
        super(String.format(ERROR_MES, categoryId));
    }
}
