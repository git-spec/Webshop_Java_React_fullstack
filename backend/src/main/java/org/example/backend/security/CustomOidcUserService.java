package org.example.backend.security;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.example.backend.model.User;
import org.example.backend.repository.UserRepo;


@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {
    private final UserRepo userRepo;

    @Override
    public OidcUser loadUser(OidcUserRequest  userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        @SuppressWarnings("unused")
        User user = userRepo.findById(oidcUser.getName())
            .orElseGet(() -> createUser(oidcUser));

        return oidcUser;
    }

    private User createUser(OidcUser oidcUser) {
        Map<String, Object> userMap = oidcUser.getAttributes();
        User newUser = new User(
            (String) userMap.get("sub"),
            (String) userMap.get("email"),
            (String) userMap.get("given_name"),
            (String) userMap.get("family_name"),
            List.of()
        );
        
        userRepo.save(newUser);
        return newUser;
    }
}
