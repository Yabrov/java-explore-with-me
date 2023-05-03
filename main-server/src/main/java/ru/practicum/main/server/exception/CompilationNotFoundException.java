package ru.practicum.main.server.exception;

public class CompilationNotFoundException extends EntityNotFoundException {

    private static final String ERROR_MES = "Compilation with id=%s was not found";

    public CompilationNotFoundException(Long compilationId) {
        super(String.format(ERROR_MES, compilationId));
    }
}
