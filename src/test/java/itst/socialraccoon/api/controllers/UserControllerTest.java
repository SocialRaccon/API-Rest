package itst.socialraccoon.api.controllers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "pedro@teziutlan.tecnm.mx", password = "123Yy456")
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    /*@Test
    public void createUser() throws Exception {
        String requestBody = "{\n" +
                "\"name\": \"Josue\",\n" +
                "\"lastName\": \"Fuentes\",\n" +
                "\"secondLastName\": \"Luna\",\n" +
                "\"email\": \"luna2227@teziutlan.tecnm.mx\",\n" +
                "\"controlNumber\": \"21TE0374\",\n" +
                "\"password\": \"Tf943535\",\n" +
                "\"career\": 1\n" +
                "}";

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("luna2227@hotmail.com"));
    }*/

    @Test
    public void deleteUser() throws Exception {
        mvc.perform(delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"deleted\":true")));
    }

    @Test
    public void deleteUserNotFound() throws Exception {
        mvc.perform(delete("/users/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
