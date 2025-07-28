package org.example.backend.model;


public record Address(
    String street,
    String postalCode,
    String locality,
    String region,
    String country
) {

}
