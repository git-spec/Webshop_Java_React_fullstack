package org.example.backend.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends Exception {

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
