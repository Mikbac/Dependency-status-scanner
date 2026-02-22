package pl.mikbac.dependencystatusscanner.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by MikBac on 03.05.2025
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNonExistingElement(final NoSuchElementException ex,
                                                                  final HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildExceptionResponse(HttpStatus.NOT_FOUND, problemDetail -> {
                    problemDetail.setTitle("Resource not found.");
                    problemDetail.setDetail(ex.getMessage());
                    problemDetail.setInstance(URI.create(request.getRequestURI()));
                }));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleConflictWithExistingElement(final IllegalArgumentException ex,
                                                                           final HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildExceptionResponse(HttpStatus.CONFLICT, problemDetail -> {
                    problemDetail.setTitle("Resource already exists.");
                    problemDetail.setDetail(ex.getMessage());
                    problemDetail.setInstance(URI.create(request.getRequestURI()));
                }));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationException(final MethodArgumentNotValidException ex,
                                                                   final HttpServletRequest request) {
        final String errorMessage = Optional.ofNullable(ex.getFieldError())
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Validation exception.");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildExceptionResponse(HttpStatus.BAD_REQUEST, problemDetail -> {
                    problemDetail.setTitle("Invalid request data.");
                    problemDetail.setDetail(errorMessage);
                    problemDetail.setInstance(URI.create(request.getRequestURI()));
                }));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnsupportedException(final Exception ex,
                                                                    final HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildExceptionResponse(HttpStatus.BAD_REQUEST, problemDetail -> {
                    problemDetail.setTitle("An unexpected error occurred.");
                    problemDetail.setDetail(ex.getMessage());
                    problemDetail.setInstance(URI.create(request.getRequestURI()));
                }));
    }

    private ProblemDetail buildExceptionResponse(final HttpStatus status, Consumer<ProblemDetail> consumer) {
        final String errorId = UUID.randomUUID().toString();
        LOGGER.warn("Generated unique error id for exception [errorId={}]", errorId);
        final ProblemDetail problem = ProblemDetail.forStatus(status);
        consumer.accept(problem);
        problem.setProperties(Map.of("errorId", errorId, "timestamp", Instant.now().toString()));
        return problem;
    }

}
