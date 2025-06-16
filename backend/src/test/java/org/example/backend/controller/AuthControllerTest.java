package org.example.backend.controller;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetMe() throws Exception {
        mockMvc
            .perform(
                get("/api/auth")    
                .with(oauth2Login().attributes(attrs -> {
                        attrs.put("sub", "1234567890");
                        attrs.put("email", "testuser@gmail.com");
                        attrs.put("name", "Test User");
                    })
                )
            ).andExpect(status().isOk());
    }
}
