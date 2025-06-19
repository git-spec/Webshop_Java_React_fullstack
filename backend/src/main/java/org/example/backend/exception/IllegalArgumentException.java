package org.example.backend.exception;

import lombok.Getter;


@Getter
public class IllegalArgumentException extends RuntimeException {

    public IllegalArgumentException(String message) {
        super(message);
    }
}
