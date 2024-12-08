package com.unidebnotes.unideb_notes_app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPublicEndpointsAllowUnauthenticatedAccess() throws Exception {
        mockMvc.perform(get("/api/users/register"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/users/verify"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/users/login"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/users/logout"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testAuthenticatedAccessIsRequiredForOtherEndpoints() throws Exception {
        mockMvc.perform(get("/api/private"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUnauthenticatedAccessIsRejectedForOtherEndpoints() throws Exception {
        mockMvc.perform(get("/api/private"))
                .andExpect(status().isUnauthorized());
    }
}
