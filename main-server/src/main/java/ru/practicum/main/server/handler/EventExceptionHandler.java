package ru.practicum.main.server.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.server.dto.error.ApiError;
import ru.practicum.main.server.exception.EventNotFoundException;
import ru.practicum.main.server.exception.EventUpdateException;

@RestControllerAdvice
public class EventExceptionHandler {

    @ResponseBody
    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> eventNotFoundExceptionHandler(EventNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiError responseBody = new ApiError(e, status, "The required object was not found.");
        return new ResponseEntity<>(responseBody, status);
    }

    @ResponseBody
    @ExceptionHandler(EventUpdateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> eventUpdateExceptionHandler(EventUpdateException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiError responseBody = new ApiError(e, status, "For the requested operation the conditions are not met.");
        return new ResponseEntity<>(responseBody, status);
    }
}
