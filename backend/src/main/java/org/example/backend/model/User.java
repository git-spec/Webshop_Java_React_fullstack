package org.example.backend.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;


@Getter
@EqualsAndHashCode 
@Document("users")
public class User {
    private final String id;
    private String sub;
    private final String email; 
    private String gender;
    private final String givenName; 
    private final String familyName;
    private Address address;
    private List<String> watchlist;
    private final Instant createdAt;

    public User(
        String id, 
        String email, 
        String givenName, 
        String familyName,  
        Instant createdAt
    ) {
        this.id = id;
        this.email = email;
        this.givenName = givenName;
        this.familyName = familyName;
        this.createdAt = createdAt;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setWatchlist(List<String> watchlist) {
        this.watchlist = watchlist;
    }
}
