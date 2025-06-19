package org.example.backend.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
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
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    @Test
    void oidcLogin_shouldSaveUser_whenIsCalled() throws Exception {
        // GIVEN
        userRepo.save(new User("123", "user@test.de", "test", "user"));
        //WHEN //THEN
        mockMvc.perform(get("/api/auth")
            .with(
                SecurityMockMvcRequestPostProcessors.oidcLogin()
                    .idToken(token -> {
                        token.claim("email", "user@test.de");
                    })
                ))
            .andExpect(status().isOk());

        User user;
        user = userRepo.findByEmail("user@test.de");
        assertEquals("test", user.firstname());
    }

    @Test
    @DirtiesContext
    void getMe() throws Exception {
        mockMvc.perform(get("/api/auth").with(SecurityMockMvcRequestPostProcessors.oidcLogin().userInfoToken(token -> token
                                .claim("email", "user@test.de")
                        ))
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "email": "user@test.de"
                        }
                        """));
    }
}
