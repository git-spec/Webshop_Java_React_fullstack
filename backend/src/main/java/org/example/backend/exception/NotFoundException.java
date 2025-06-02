package org.example.backend.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends Exception {

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
