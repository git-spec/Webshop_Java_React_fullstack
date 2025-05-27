package org.example.backend.models;

import lombok.Getter;


@Getter
public enum Colors {
    BLACK("Black"),
    WHITE("White"),
    RED("Red"),
    BLUE("Blue"),
    GREEN("Green"),
    YELLOW("Yellow"),
    ORANGE("Orange"),
    VIOLETT("Violett"),
    BROWN("Braun"),
    OAK("Oak"),
    BEECH("Beech"),
    WALNUT("Walnut"),
    SPRUCE("Spruce"),
    LARCH("Larch");

    private final String value;

    Colors(String value) {
        this.value = value;
    }
}
