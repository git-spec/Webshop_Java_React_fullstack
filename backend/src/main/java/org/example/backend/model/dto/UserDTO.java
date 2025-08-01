package org.example.backend.model.dto;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import org.example.backend.model.Address;


@Getter
@EqualsAndHashCode
public class UserDTO {
    private String sub;
    private final String email; 
    private String gender;
    private final String givenName; 
    private final String familyName;
    private Address address;
    private List<String> watchlist;


    public UserDTO( 
        String email, 
        String givenName, 
        String familyName
    ) {
        this.email = email;
        this.givenName = givenName;
        this.familyName = familyName;
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
