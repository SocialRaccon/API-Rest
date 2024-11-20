package itst.socialraccoon.api.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PostController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void getPostsByUserId() throws Exception {
        mvc.perform(get("/posts/1?page=0&size=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].postDescription").exists());
    }

    @Test
    public void getPostsByUserIdNotFoundTest() throws Exception {
        mvc.perform(get("/posts/0?page=0&size=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFeed() throws Exception {
        mvc.perform(get("/posts/feed?page=0&size=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].postDescription").exists());
    }

    @Test
    public void createPostWithoutImage() throws Exception {
        mvc.perform(post("/posts/1")
                        .param("postDescription", "This is a post without image")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.postDescription", String.class).value("This is a post without image"));
    }

    /*@Test
    public void createPostWithImage() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image data".getBytes());
        mvc.perform(MockMvcRequestBuilders.multipart("/posts/withImage/1")
                        .file(image)
                        .param("postDescription", "This is a post with image"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.postDescription", String.class).value("This is a post with image"));
    }*/

    @Test
    public void deletePost() throws Exception {
        mvc.perform(delete("/posts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Post deleted successfully"));
    }

    @Test
    public void deleteImageFromPost() throws Exception {
        mvc.perform(delete("/posts/images/1")
                        .param("imageId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Image deleted successfully"));
    }

    /*@Test
    public void addImageToPost() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image data".getBytes());
        mvc.perform(MockMvcRequestBuilders.multipart("/posts/images/1")
                        .file(image))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Image added successfully"));
    }*/

    @Test
    public void getImagesFromPost() throws Exception {
        mvc.perform(get("/posts/images/2?page=0&size=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /*@Test
    public void updateImageFromPost() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "updated image data".getBytes());
        mvc.perform(MockMvcRequestBuilders.multipart("/posts/images/2")
                        .file(image)
                        .param("imageId", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Image added successfully"));
    }*/
}
