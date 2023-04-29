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
import ru.practicum.main.server.exception.*;

@RestControllerAdvice
public class GenericExceptionHandler {

    @ResponseBody
    @ExceptionHandler({
            UserNotFoundException.class,
            EventNotFoundException.class,
            RequestNotFoundException.class,
            CompilationNotFoundException.class,
            CategoryNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> requiredObjectNotFoundExceptionHandler(Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiError responseBody = new ApiError(e, status, "The required object was not found.");
        return new ResponseEntity<>(responseBody, status);
    }

    @ResponseBody
    @ExceptionHandler({
            RequestStateUpdateException.class,
            RequestCreationException.class,
            EventUpdateException.class,
            EventCreationException.class,
            CategoryIsNotEmptyException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> conflictExceptionHandler(Exception e) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiError responseBody = new ApiError(e, status, "For the requested operation the conditions are not met.");
        return new ResponseEntity<>(responseBody, status);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> violationExceptionHandler(ConstraintViolationException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiError responseBody = new ApiError(e, status, "Integrity constraint has been violated.");
        return new ResponseEntity<>(responseBody, status);
    }

    @ResponseBody
    @ExceptionHandler({
            RequestWrongStateException.class,
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
