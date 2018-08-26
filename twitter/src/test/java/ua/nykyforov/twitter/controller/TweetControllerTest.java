package ua.nykyforov.twitter.controller;

import org.hamcrest.core.IsSame;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import ua.nykyforov.twitter.Main;
import ua.nykyforov.twitter.domain.Tweet;
import ua.nykyforov.twitter.service.TweetService;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @AfterEach
    void tearDown() {
        Mockito.reset(tweetService);
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

    @Nested
    @DisplayName("edit")
    class Edit {

        @Test
        void shouldReturnPage() throws Exception {
            final String tweetId = "tweetId";
            Tweet tweet = new Tweet("What's happening?");
            doReturn(Optional.of(tweet)).when(tweetService).findById(eq(tweetId));

            mockMvc.perform(get("/edit/{id}", tweetId))
                    .andExpect(view().name("edit"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("tweet", IsSame.sameInstance(tweet)));
            verify(tweetService, times(1)).findById(eq(tweetId));
        }

        @Test
        void shouldSaveEntity() throws Exception {
            final String tweetId = "id";
            final String tweetText = "What's happening?";
            Tweet tweet = new Tweet(tweetText);
            doReturn(Optional.of(tweet)).when(tweetService).findById(eq(tweetId));

            LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("id", tweetId);
            params.add("text", tweetText);
            mockMvc.perform(post("/edit").params(params))
                    .andExpect(view().name("redirect:/"))
                    .andExpect(status().is3xxRedirection());
            verify(tweetService, times(1)).save(argThat(e ->
                    e != null && Objects.equals(tweetText, e.getText())
            ));
        }

    }


}