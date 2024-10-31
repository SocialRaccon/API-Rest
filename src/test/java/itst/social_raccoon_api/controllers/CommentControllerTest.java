package itst.social_raccoon_api.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
    public void createCommentTest() throws Exception {
        String requestBody = "{\n" +
                "  \"comment\": \"Nuevo comentario\",\n" +
                "  \"date\": \"2024-10-20T15:10:00.000+00:00\",\n" +
                "  \"user\": {\n" +
                "    \"idUser\": 1\n" +
                "  }\n" +
                "}";

        mvc.perform(post("/comments/post/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.comment", is("Nuevo comentario")));
    }

    @Test
    public void getCommentsByPostIdTest() throws Exception {
        mvc.perform(get("/comments/post/1?page=0&pageSize=10").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(lessThanOrEqualTo(10))));
    }

    @Test
    public void getCommentsByPostIdWithUserTest() throws Exception {
        mvc.perform(get("/comments/post/1?userId=2&page=0&pageSize=10").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(lessThanOrEqualTo(10))))
                .andExpect(jsonPath("$[*].user.idUser", everyItem(equalTo(2))));
    }

    @Test
    public void getCommentsByUserIdTest() throws Exception {
        mvc.perform(get("/comments/user/1?page=0&pageSize=10").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(lessThanOrEqualTo(10))));
    }

    @Test
    public void getCommentsByPostIdNotFoundTest() throws Exception {
        mvc.perform(get("/comments/post/0?page=0&pageSize=10").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("The requested item is not registered")));
    }

    @Test
    public void getCommentsByUserIdNotFoundTest() throws Exception {
        mvc.perform(get("/comments/user/0?page=0&pageSize=10").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("The requested item is not registered")));
    }

    @Test
    public void updateCommentTest() throws Exception {
        String requestBody = "{\n" +
                "  \"comment\": \"Comentario actualizado\",\n" +
                "  \"date\": \"2024-10-20T15:10:00.000+00:00\"\n" +
                "}";
        mvc.perform(put("/comments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment", is("Comentario actualizado")));
    }

    @Test
    public void deleteCommentTest() throws Exception {
        mvc.perform(delete("/comments/2"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}