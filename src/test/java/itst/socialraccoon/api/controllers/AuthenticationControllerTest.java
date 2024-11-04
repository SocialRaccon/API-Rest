package itst.socialraccoon.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testLoginSuccessful() throws Exception {
        String jsonLogin = "{ \"email\": \"juan@gmail.com\", \"password\": \"123456\" }";

        mockMvc.perform(post("/authentications/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLogin))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));
    }

    @Test
    public void testRecoverPasswordEmailFound() throws Exception {
        String jsonRecover = "{ \"email\": \"maria@gmail.com\" }";

        mockMvc.perform(post("/authentications/recover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRecover))
                .andExpect(status().isOk())
                .andExpect(content().string("Password recovery email sent"));
    }

    @Test
    public void testChangePasswordSuccessful() throws Exception {
        String jsonChangePassword = "{\r\n" + //
                "  \"email\": \"pedro@gmail.com\",\r\n" + //
                "  \"password\": \"123456\",\r\n" + //
                "  \"newPassword\": \"!#$dd1234\"\r\n" + //
                "}";

        mockMvc.perform(put("/authentications/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonChangePassword))
                .andExpect(status().isOk())
                .andExpect(content().string("Password changed successfully"));
    }
}