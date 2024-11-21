package itst.socialraccoon.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "pedro@teziutlan.tecnm.mx", password = "123Yy456")
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

 /*   @Test
    public void testLoginSuccessful() throws Exception {
        String jsonLogin = "{ \"email\": \"pedro@teziutlan.tecnm.mx\", \"password\": \"123Yy456\" }";

        mockMvc.perform(post("/authentications/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLogin))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));
    }*/

    @Test
    public void testRecoverPasswordEmailFound() throws Exception {
        String jsonRecover = "{ \"email\": \"pedro@teziutlan.tecnm.mx\" }";

        mockMvc.perform(post("/authentications/recover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRecover))
                .andExpect(status().isOk())
                .andExpect(content().string("Password recovery email sent"));
    }

    /*@Test
    public void testChangePasswordSuccessful() throws Exception {
        String jsonChangePassword = "{\r\n" + //
                "  \"email\": \"maria@teziutlan.tecnm.mx\",\r\n" + //
                "  \"password\": \"12fF3456\",\r\n" + //
                "  \"newPassword\": \"12fF3456\"\r\n" + //
                "}";

        mockMvc.perform(put("/authentications/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonChangePassword))
                .andExpect(status().isOk())
                .andExpect(content().string("Password changed successfully"));
    }*/
}