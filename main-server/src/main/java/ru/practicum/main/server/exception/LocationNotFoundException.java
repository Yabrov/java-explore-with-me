package ru.practicum.main.server.exception;

public class LocationNotFoundException extends EntityNotFoundException {

    private static final String ERROR_MES = "Location with id=%s was not found";

    public LocationNotFoundException(Long categoryId) {
        super(String.format(ERROR_MES, categoryId));
    }
}