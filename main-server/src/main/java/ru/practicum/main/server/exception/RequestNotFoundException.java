package ru.practicum.main.server.exception;

public class RequestNotFoundException extends RuntimeException {

    private static final String ERROR_MES = "Request with id=%s was not found";

    public RequestNotFoundException(Long requestId) {
        super(String.format(ERROR_MES, requestId));
    }
}
