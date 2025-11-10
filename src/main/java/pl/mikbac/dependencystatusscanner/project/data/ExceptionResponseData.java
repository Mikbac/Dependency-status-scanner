package pl.mikbac.dependencystatusscanner.project.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

/**
 * Created by MikBac on 01.06.2025
 */

@Builder
public record ExceptionResponseData(String message,
                                    String path,
                                    int errorCode,
                                    String errorId,
                                    Instant timestamp) {
    @AllArgsConstructor
    @Getter
    public enum ExceptionErrorCode {
        VALIDATION_ERROR(10001),
        DUPLICATE_ELEMENT_ERROR(10002),
        ELEMENT_NOT_FOUND_ERROR(10003),
        UNKNOWN_ERROR(99999);

        private final int code;
    }
}
