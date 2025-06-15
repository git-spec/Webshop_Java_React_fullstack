package org.example.backend.model.dto;

import lombok.With;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import java.time.Instant;

import org.example.backend.exception.WithPathInterface;


@With
public record ErrorDTO(
        String error,
        String cause,
        String causeMessage,
        String message,
        String timestamp,
        String status,
        String path // An optional path to connect this error message to a form field.
) {
    /**
     * Converts an exception into ErrorDTO object.
     *
     * @param exception - exception to convert
     * @return ErrorDTO object with all informations
     */
    public static ErrorDTO fromException(final Exception exception) {
        Throwable cause = exception.getCause();
        String path = null;
        String error = exception.getClass().getSimpleName();
        if (exception instanceof WithPathInterface) {
            path = ((WithPathInterface) exception).getPath();
        }
        return new ErrorDTO(
                error,
                cause != null ? cause.getClass().getSimpleName() : null,
                cause != null ? cause.getMessage() : null,
                StringUtils.capitalize(exception.getMessage()),
                Instant.now().toString(),
                HttpStatus.BAD_REQUEST.name(),
                path
        );
    }
}
