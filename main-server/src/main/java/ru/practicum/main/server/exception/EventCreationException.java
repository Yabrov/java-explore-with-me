package ru.practicum.main.server.exception;

import java.time.LocalDateTime;

public class EventCreationException extends RuntimeException {

    private static final String ERROR_MES = "Field: eventDate. Error: должно содержать дату, которая еще не наступила. Value: %s";

    public EventCreationException(LocalDateTime eventDate) {
        super(String.format(ERROR_MES, eventDate.toString()));
    }
}
