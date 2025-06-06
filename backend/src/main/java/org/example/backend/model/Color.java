package org.example.backend.model;

import lombok.Getter;


@Getter
public enum Color {
    BLACK("black"),
    WHITE("white"),
    RED("red"),
    BLUE("blue"),
    GREEN("green"),
    YELLOW("yellow"),
    ORANGE("orange"),
    VIOLETT("violett"),
    BROWN("braun"),
    OAK("oak"),
    ASH("ash"),
    ASH_BLACK("ash stained black"),
    BEECH("beech"),
    WALNUT("walnut"),
    SPRUCE("spruce"),
    LARCH("larch");

    private final String value;

    Color(String value) {
        this.value = value;
    }
}
