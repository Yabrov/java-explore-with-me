package ru.practicum.main.server.exception;

public class RequestViolationException extends RuntimeException {

    public RequestViolationException(Throwable cause) {
        super(cause);
    }
}
