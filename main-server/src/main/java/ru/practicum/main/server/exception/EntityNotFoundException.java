package ru.practicum.main.server.exception;

public abstract class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String mes) {
        super(mes);
    }
}
