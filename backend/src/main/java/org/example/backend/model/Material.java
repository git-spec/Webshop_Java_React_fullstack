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
    METAL("Metal"),
    IRON("Iron"),
    STEEL("Steel"),
    STAINLESS("Stainless Steel"),
    ALUMINIUM("Aluminium"),
    GLASS("Glass"),
    WOOL("Wool"),
    VIRGIN_WOOL("Virgin Wool"),
    LINEN("Linen"),
    COTTON("Cotton"),
    HEMP( "Hanf"),
    LEATHER("Leather"),
    LINOLEUM("Linoleum"),
    PLASTIC("Plastic");

    private final String value;

    Material(String value) {
        this.value = value;
    }
}
