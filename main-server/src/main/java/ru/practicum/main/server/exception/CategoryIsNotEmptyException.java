package ru.practicum.main.server.exception;

public class CategoryIsNotEmptyException extends EntityConflictException {

    public CategoryIsNotEmptyException() {
        super("The category is not empty");
    }
}
