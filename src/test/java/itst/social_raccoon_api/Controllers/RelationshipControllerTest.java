package itst.social_raccoon_api.Controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        mvc.perform(post("/relationships/1/3") // Juan follows Pedro
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User followed"));
    }

    @Test
    public void unfollowUserTest() throws Exception {
        mvc.perform(delete("/relationships/1/2") // Juan stops following María
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User unfollowed"));
    }

    @Test
    public void getFollowersAndFollowingTest() throws Exception {
        mvc.perform(get("/relationships/1") // Get information from Juan
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.followers", hasSize(1))) // Juan has 2 followers
                .andExpect(jsonPath("$.following", hasSize(2))); // Juan follows 2 users
    }

    @Test
    public void getFollowersAndFollowingNotFoundTest() throws Exception {
        mvc.perform(get("/relationships/10") // Search for a user that does not exist
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}