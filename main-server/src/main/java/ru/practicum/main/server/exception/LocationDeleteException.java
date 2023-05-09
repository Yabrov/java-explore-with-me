package ru.practicum.main.server.exception;

public class LocationDeleteException extends EntityConflictException {

    private static final String PATTERN = "Location with id=%s contains events";

    public LocationDeleteException(Long id) {
        super(String.format(PATTERN, id));
    }
}
