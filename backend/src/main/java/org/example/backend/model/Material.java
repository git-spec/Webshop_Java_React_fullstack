package org.example.backend.model;

import lombok.Getter;


@Getter
public enum Material {
    OAK("Oak"),
    ASH("Ash"),
    BEECH("Beech"),
    WALNUT("Walnut"),
    SPRUCE("Spruce"),
    LARCH("Larch"),
    IRON("Iron"),
    STEEL("Steel"),
    STAINLESS("Stainless Steel"),
    ALUMINIUM("Aluminium"),
    GLASS("Glass"),
    WOOL("Wool"),
    LINEN("Linen"),
    COTTON("Cotton"),
    LINOLEUM("Linoleum");

    private final String value;

    Material(String value) {
        this.value = value;
    }
}
