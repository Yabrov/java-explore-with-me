package ru.practicum.main.server.exception;

public abstract class EntityConflictException extends RuntimeException {

    public EntityConflictException(String mes) {
        super(mes);
    }
}
