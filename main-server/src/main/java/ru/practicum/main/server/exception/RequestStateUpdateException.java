package ru.practicum.main.server.exception;

public class RequestStateUpdateException extends EntityConflictException {

    public RequestStateUpdateException(String message) {
        super(message);
    }
}
