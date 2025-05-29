package org.example.backend.model;

import lombok.Getter;


@Getter
public enum Color {
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
    ASH("Ash"),
    BEECH("Beech"),
    WALNUT("Walnut"),
    SPRUCE("Spruce"),
    LARCH("Larch");

    private final String value;

    Color(String value) {
        this.value = value;
    }
}
