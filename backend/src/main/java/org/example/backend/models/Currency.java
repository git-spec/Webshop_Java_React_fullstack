package org.example.backend.models;

import lombok.Getter;


@Getter
public enum Currency {
    EUR('€'),
    JPY('¥'),
    USD('$'),
    GBP('£');

    private final char value;

    Currency(char value) {
        this.value = value;
    }
}
