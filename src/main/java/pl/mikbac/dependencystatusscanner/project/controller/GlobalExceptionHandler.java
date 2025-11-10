package pl.mikbac.dependencystatusscanner.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.mikbac.dependencystatusscanner.project.data.ExceptionResponseData;
import pl.mikbac.dependencystatusscanner.project.data.ExceptionResponseData.ExceptionErrorCode;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by MikBac on 03.05.2025
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponseData> handleNonExistingElement(final NoSuchElementException ex,
                                                                          final HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildExceptionResponse(ex.getMessage(), request.getRequestURI(), ExceptionErrorCode.ELEMENT_NOT_FOUND_ERROR));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseData> handleConflictWithExistingElement(final IllegalArgumentException ex,
                                                                                   final HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildExceptionResponse(ex.getMessage(), request.getRequestURI(), ExceptionErrorCode.DUPLICATE_ELEMENT_ERROR));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseData> handleValidationException(final MethodArgumentNotValidException ex,
                                                                           final HttpServletRequest request) {
        final String errorMessage = Optional.ofNullable(ex.getFieldError())
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Validation exception.");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildExceptionResponse(errorMessage, request.getRequestURI(), ExceptionErrorCode.VALIDATION_ERROR));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseData> handleUnsupportedException(final Exception ex,
                                                                            final HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildExceptionResponse(ex.getMessage(), request.getRequestURI(), ExceptionErrorCode.UNKNOWN_ERROR));
    }

    private ExceptionResponseData buildExceptionResponse(final String message,
                                                         final String uri,
                                                         final ExceptionErrorCode errorCode) {
        final String errorId = UUID.randomUUID().toString();
        LOGGER.warn("Generated unique error id for exception [errorId={}]", errorId);
        return ExceptionResponseData.builder()
                .message(message)
                .path(uri)
                .errorCode(errorCode.getCode())
                .errorId(errorId)
                .timestamp(Instant.now())
                .build();
    }

}
