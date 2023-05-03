package ru.practicum.main.server.exception;

public class RequestCreationException extends EntityConflictException {

    public RequestCreationException(String message) {
        super(message);
    }
}
