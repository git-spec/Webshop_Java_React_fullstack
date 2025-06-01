package org.example.backend.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends Exception {
    private final String path;


    public NotFoundException(String message, String path) {
        super(message);
        this.path = path;
    }
}
