package ru.practicum.main.server.user.exception;

public class UserViolationException extends RuntimeException {

    public UserViolationException(Throwable throwable) {
        super(throwable);
    }
}
