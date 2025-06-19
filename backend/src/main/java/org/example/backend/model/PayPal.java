package org.example.backend.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PayPal {
    private final String id;
    private final String email;
    private final String firstname;
    private final String lastname;
}
