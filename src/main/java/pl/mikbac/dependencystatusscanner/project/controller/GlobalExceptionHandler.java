package pl.mikbac.dependencystatusscanner.project.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.mikbac.dependencystatusscanner.project.data.ExceptionResponseData;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by MikBac on 03.05.2025
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponseData> handleNonExistingElement(final NoSuchElementException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseData> handleConflictWithExistingElement(final IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseData> handleValidationException(final MethodArgumentNotValidException ex) {
        final String errorMessage = Optional.ofNullable(ex.getFieldError())
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Validation exception.");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildExceptionResponse(errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseData> handleUnsupportedException(final Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildExceptionResponse(ex.getMessage()));
    }

    private ExceptionResponseData buildExceptionResponse(final String message) {
        return ExceptionResponseData.builder().message(message).build();
    }

}
