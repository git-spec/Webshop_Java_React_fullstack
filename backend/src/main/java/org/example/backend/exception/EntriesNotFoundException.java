package org.example.backend.exception;

import lombok.Getter;


@Getter
public class EntriesNotFoundException extends RuntimeException {

    public EntriesNotFoundException(String message) {
        super(message);
    }
}
