package ru.practicum.main.server.user.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.main.server.config.ApiError;
import ru.practicum.main.server.user.exception.UserNotFoundException;
import ru.practicum.main.server.user.exception.UserViolationException;

@RestControllerAdvice
public class ExceptionController {

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

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> userNotFoundExceptionHandler(UserNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiError responseBody = new ApiError(e, status, "The required object was not found.");
        return new ResponseEntity<>(responseBody, status);
    }

    @ResponseBody
    @ExceptionHandler(UserViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> userViolationExceptionHandler(UserViolationException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiError responseBody = new ApiError(e, status, "Integrity constraint has been violated.");
        return new ResponseEntity<>(responseBody, status);
    }
}
