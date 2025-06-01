package org.example.backend.exception;

import lombok.Getter;

@Getter
public class ArgumentNotFoundException extends IllegalArgumentException implements WithPathInterface {
    private final String path;

    public ArgumentNotFoundException(String message, String path) {
        super(message);
        this.path = path;
    }

    String getMess() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}