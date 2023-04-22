package ru.practicum.main.server.exception;

public class CategoryIsNotEmptyException extends RuntimeException {

    public CategoryIsNotEmptyException() {
        super("The category is not empty");
    }
}
