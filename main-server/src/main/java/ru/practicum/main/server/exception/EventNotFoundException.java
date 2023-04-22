package ru.practicum.main.server.exception;

public class EventNotFoundException extends RuntimeException {

    private static final String ERROR_MES = "Event with id=%s was not found";

    public EventNotFoundException(Long userId) {
        super(String.format(ERROR_MES, userId));
    }
}
