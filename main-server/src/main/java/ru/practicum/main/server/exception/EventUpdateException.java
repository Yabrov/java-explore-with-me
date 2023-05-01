package ru.practicum.main.server.exception;

public class EventUpdateException extends EntityConflictException {

    public EventUpdateException(String message) {
        super(message);
    }
}
