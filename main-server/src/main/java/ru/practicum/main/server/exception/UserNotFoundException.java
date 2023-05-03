package ru.practicum.main.server.exception;

public class UserNotFoundException extends EntityNotFoundException {

    private static final String ERROR_MES = "User with id=%s was not found";

    public UserNotFoundException(Long userId) {
        super(String.format(ERROR_MES, userId));
    }
}
