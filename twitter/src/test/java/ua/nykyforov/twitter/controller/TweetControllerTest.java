package ua.nykyforov.twitter.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.nykyforov.twitter.Main;
import ua.nykyforov.twitter.service.TweetService;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SuppressWarnings("WeakerAccess")
@SpringJUnitWebConfig(classes = Main.class)
@ExtendWith(MockitoExtension.class)
class TweetControllerTest {

    @Autowired
    WebApplicationContext wac;

    @MockBean
    TweetService tweetService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Nested
    @DisplayName("add")
    class Add {

        @Test
        void shouldReturnPage() throws Exception {
            mockMvc.perform(get("/add"))
                    .andExpect(view().name("add"))
                    .andExpect(status().isOk());
        }

        @Test
        void shouldSaveEntity() throws Exception {
            String tweetText = "What's happening?";
            mockMvc.perform(post("/add").param("text", tweetText))
                    .andExpect(view().name("redirect:/"))
                    .andExpect(status().is3xxRedirection());
            verify(tweetService, times(1)).save(argThat(e ->
                    e != null && Objects.equals(tweetText, e.getText())
            ));
        }

    }


}