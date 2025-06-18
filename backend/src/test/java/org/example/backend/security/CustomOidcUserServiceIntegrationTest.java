package org.example.backend.security;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import org.example.backend.repository.UserRepo;
import org.example.backend.model.User;


@SpringBootTest
@AutoConfigureMockMvc
public class CustomOidcUserServiceIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepo userRepo;

    @Test
    void oidcLogin_shouldSaveUser_whenIsCalled() throws Exception {
        mvc.perform(get("/api/orders")
            .with(
                SecurityMockMvcRequestPostProcessors.oidcLogin()
                    .idToken(token -> {
                        token.claim("email", "user@test.de");
                        token.claim("given_name", "testuser");
                    })
                ))
            .andExpect(status().isOk());

        User user;
        user = userRepo.findByEmail("user@test.de");
        assertEquals("testuser", user.firstname());
    }
}
