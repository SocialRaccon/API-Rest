package itst.social_raccoon_api.Controllers;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import itst.social_raccoon_api.Models.ReactionModel;
import itst.social_raccoon_api.Services.ReactionService;

public class ReactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ReactionService reactionService;

    @InjectMocks
    private ReactionController reactionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reactionController).build();
    }

    @Test
    public void testGetAllReactions() throws Exception {
        ReactionModel reaction1 = new ReactionModel(1, new Timestamp(System.currentTimeMillis()));
        ReactionModel reaction2 = new ReactionModel(2, new Timestamp(System.currentTimeMillis()));
        List<ReactionModel> reactions = Arrays.asList(reaction1, reaction2);

        when(reactionService.findAll()).thenReturn(reactions);

        mockMvc.perform(get("/reaction")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].idReaction").value(1))
                .andExpect(jsonPath("$[1].idReaction").value(2));
    }

    @Test
    public void testGetReactionById() throws Exception {
        ReactionModel reaction = new ReactionModel(1, new Timestamp(System.currentTimeMillis()));
        
        when(reactionService.findById(1)).thenReturn(reaction);

        mockMvc.perform(get("/reaction/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReaction").value(1));
    }

    @Test
    public void testGetReactionByIdNotFound() throws Exception {
        when(reactionService.findById(99)).thenReturn(null);

        mockMvc.perform(get("/reaction/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
}
