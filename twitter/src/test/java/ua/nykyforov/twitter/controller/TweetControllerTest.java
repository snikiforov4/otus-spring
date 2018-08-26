package ua.nykyforov.twitter.controller;

import org.assertj.core.util.Lists;
import org.hamcrest.core.IsSame;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import ua.nykyforov.twitter.Main;
import ua.nykyforov.twitter.domain.Tweet;
import ua.nykyforov.twitter.service.TweetService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("WeakerAccess")
@SpringJUnitWebConfig(classes = Main.class)
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
    @DisplayName("/add")
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
    @DisplayName("/edit")
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

    }

    @Nested
    @DisplayName("/edit/{id}")
    class EditById {

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

    @Nested
    @DisplayName("/delete/{id}")
    class Delete {

        @Test
        void shouldReturnPage() throws Exception {
            final String tweetId = "tweetId";

            mockMvc.perform(get("/delete/{id}", tweetId))
                    .andExpect(view().name("redirect:/"))
                    .andExpect(status().is3xxRedirection());
            verify(tweetService, times(1)).deleteById(eq(tweetId));
        }

    }

    @Nested
    @DisplayName("/")
    class Root {

        @Test
        void shouldReturnPage() throws Exception {
            String firstTweetText = "What is happening?";
            String secondTweetText = "Spring Web Mvc is awesome!";
            List<Tweet> tweets = Lists.newArrayList(new Tweet(firstTweetText), new Tweet(secondTweetText));
            doReturn(tweets).when(tweetService).findAll();

            MvcResult result = mockMvc.perform(get("/"))
                    .andExpect(view().name("index"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("tweets", IsSame.sameInstance(tweets)))
                    .andReturn();
            String content = result.getResponse().getContentAsString();
            assertThat(content).contains(firstTweetText, secondTweetText);
            verify(tweetService, times(1)).findAll();
        }

    }


}