package ru.practicum.main.server.exception;

public class RequestWrongStateException extends RuntimeException {

    public RequestWrongStateException() {
        super("Request must have status PENDING");
    }
}
