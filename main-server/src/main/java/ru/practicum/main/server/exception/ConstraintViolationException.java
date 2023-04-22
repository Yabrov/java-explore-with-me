package ru.practicum.main.server.exception;

public class ConstraintViolationException extends RuntimeException {

    public ConstraintViolationException(Throwable throwable) {
        super(throwable);
    }
}
