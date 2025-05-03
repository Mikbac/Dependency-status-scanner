package pl.mikbac.dependencystatusscanner.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

/**
 * Created by MikBac on 03.05.2025
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Void> handleIllegalArgument() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND).build();
    }

}
