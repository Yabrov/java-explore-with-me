package ru.practicum.main.server.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.server.dto.error.ApiError;
import ru.practicum.main.server.exception.CompilationNotFoundException;

@RestControllerAdvice
public class CompilationExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CompilationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> compilationNotFoundExceptionHandler(CompilationNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiError responseBody = new ApiError(e, status, "The required object was not found.");
        return new ResponseEntity<>(responseBody, status);
    }
}
