package org.example.backend.exception;

import lombok.Getter;


@Getter
public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException(String message) {
        super(message);
    }
}
