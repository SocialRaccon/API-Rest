package itst.social_raccoon_api.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class ReactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReactionController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test // Test for the getAll method
    public void getAllTest() throws Exception {
        mockMvc.perform(get("/reaction").accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test // Test for the getById method
    public void getByIdTest() throws Exception {
        mockMvc.perform(get("/reaction/post/1/user/2").accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idPost", is(1)));
    }

    @Test // Test for the getById method when the reaction is not found
    public void getByIdNotFoundTest() throws Exception {
        mockMvc.perform(get("/reaction/post/0/user/0").accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("The requested item is not registered")));
    }

    @Test // Test for the getReactionsByPostId method
    public void getReactionsByPostIdTest() throws Exception {
        mockMvc.perform(get("/reaction/post/1").accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test // Test for the getReactionsByPostId method when the post is not found
    public void getReactionsByPostIdNotFoundTest() throws Exception {
        mockMvc.perform(get("/reaction/post/0").accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("The requested item is not registered")));
    }

    @Test // Test for the getReactionsByUserId method
    public void getReactionsByUserIdTest() throws Exception {
        mockMvc.perform(get("/reaction/user/1").accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test // Test for the getReactionsByUserId method when the user is not found
    public void getReactionsByUserIdNotFoundTest() throws Exception {
        mockMvc.perform(get("/reaction/user/0").accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("The requested item is not registered")));
    }

    @Test // Test for the getReactionCountByPostId method
    public void getReactionCountByPostIdTest() throws Exception {
        mockMvc.perform(get("/reaction/count/post/1").accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", is(greaterThan(0))));
    }

    @Test // Test for the getReactionCountByPostId method when the post is not found
    public void getReactionCountByPostIdNotFoundTest() throws Exception {
        mockMvc.perform(get("/reaction/count/post/0").accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("The requested item is not registered")));
    }

    @Test // Test for the getReactionCountByPostIdAndReactionTypeId method
    public void getReactionCountByPostIdAndReactionTypeIdTest() throws Exception {
        mockMvc.perform(get("/reaction/count/post/1/reactionType/1").accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", (is(greaterThanOrEqualTo(0)))));
    }

    @Test // Test for the getReactionCountByPostIdAndReactionTypeId method when the post is not found
    public void getReactionCountByPostIdAndReactionTypeIdNotFoundTest() throws Exception {
        mockMvc.perform(get("/reaction/count/post/0/reactionType/1").accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("The requested item is not registered")));
    }
    
}
