package itst.social_raccoon_api.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RelationshipControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ReactionController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void followUserTest() throws Exception {
        mvc.perform(post("/relationships/1/3") // Juan sigue a Pedro
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User followed"));
    }

    @Test
    public void unfollowUserTest() throws Exception {
        mvc.perform(delete("/relationships/1/2") // Juan deja de seguir a María
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User unfollowed"));
    }

    @Test
    public void getFollowersAndFollowingTest() throws Exception {
        mvc.perform(get("/relationships/1") // Obtener información de Juan
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.followers", hasSize(1))) // Juan tiene 2 seguidores
                .andExpect(jsonPath("$.following", hasSize(2))); // Juan sigue a 2 usuarios
    }

    @Test
    public void getFollowersAndFollowingNotFoundTest() throws Exception {
        mvc.perform(get("/relationships/10") // Buscar un usuario que no existe
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}