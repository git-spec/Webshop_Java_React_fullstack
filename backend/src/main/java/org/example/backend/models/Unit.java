package org.example.backend.models;

import lombok.Getter;


@Getter
public enum Unit {
    MM("mm"),
    CM("cm"),
    M("m"),
    G("gr"),
    KG("kg"),
    ML("ml"),
    L("ltr");

    private final String value;

    Unit(String value) {
        this.value = value;
    }
}
