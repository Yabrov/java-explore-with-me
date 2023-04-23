package ru.practicum.main.server.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.server.dto.error.ApiError;
import ru.practicum.main.server.exception.RequestStateUpdateException;
import ru.practicum.main.server.exception.RequestWrongStateException;

@RestControllerAdvice
public class RequestExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RequestStateUpdateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> requestStateUpdateExceptionHandler(RequestStateUpdateException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiError responseBody = new ApiError(e, status, "For the requested operation the conditions are not met.");
        return new ResponseEntity<>(responseBody, status);
    }

    @ResponseBody
    @ExceptionHandler(RequestWrongStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> requestWrongStateExceptionHandler(RequestWrongStateException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError responseBody = new ApiError(e, status, "Incorrectly made request.");
        return new ResponseEntity<>(responseBody, status);
    }
}
