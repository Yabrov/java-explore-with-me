package ru.practicum.main.server.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.main.server.dto.error.ApiError;
import ru.practicum.main.server.exception.ConstraintViolationException;

@RestControllerAdvice
public class GenericExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> userViolationExceptionHandler(ConstraintViolationException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiError responseBody = new ApiError(e, status, "Integrity constraint has been violated.");
        return new ResponseEntity<>(responseBody, status);
    }

    @ResponseBody
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> validationExceptionHandler(Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError responseBody = new ApiError(e, status, "Incorrectly made request.");
        return new ResponseEntity<>(responseBody, status);
    }
}