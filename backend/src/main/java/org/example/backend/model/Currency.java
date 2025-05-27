package org.example.backend.model;

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
