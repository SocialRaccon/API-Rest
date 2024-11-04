package itst.socialraccoon.api.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CommentController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void createCommentForPost() throws Exception {
        String requestBody = "{\n" +
                "  \"comment\": \"This is a comment\",\n" +
                "  \"date\": \"2021-10-03T05:00:00.000+00:00\",\n" +
                "  \"user\": {\n" +
                "    \"idUser\": 1\n" +
                "  }\n" +
                "}";
        mvc.perform(post("/comments/post/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment", String.class).value("This is a comment"));
    }

    @Test
    public void deleteComment() throws Exception {
        mvc.perform(delete("/comments/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void getCommentsByPostId() throws Exception {
        mvc.perform(get("/comments/post/2?page=0&pageSize=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment").exists());
    }

    @Test
    public void getCommentsByPostIdNotFoundTest() throws Exception {
        mvc.perform(get("/comments/post/0?page=0&pageSize=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("The requested item is not registered")));
    }

    @Test
    public void getCommentsByUserId() throws Exception {
        mvc.perform(get("/comments/user/1?page=0&pageSize=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment").exists());
    }

    @Test
    public void getCommentsByUserIdNotFoundTest() throws Exception {
        mvc.perform(get("/comments/user/0?page=0&pageSize=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("The requested item is not registered")));
    }

    @Test
    public void updateComment() throws Exception {
        String requestBody = "{\n" +
                "  \"comment\": \"Comentario actualizado\",\n" +
                "  \"date\": \"2024-10-20T15:10:00.000+00:00\"\n" +
                "}";
        mvc.perform(put("/comments/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment", String.class).value("Comentario actualizado"));
    }

    @Test
    public void updateCommentNotFound() throws Exception {
        String requestBody = "{\n" +
                "  \"comment\": \"Comentario actualizado\",\n" +
                "  \"date\": \"2024-10-20T15:10:00.000+00:00\"\n" +
                "}";
        mvc.perform(put("/comments/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("The requested item is not registered")));
    }
}