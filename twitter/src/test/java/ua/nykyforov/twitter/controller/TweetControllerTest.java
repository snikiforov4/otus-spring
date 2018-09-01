package ua.nykyforov.twitter.controller;

import org.assertj.core.util.Lists;
import org.hamcrest.core.IsSame;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
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
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysExpect(status().isOk())
                .alwaysExpect(content().contentType("application/json;charset=UTF-8"))
                .build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(tweetService);
    }

    @Nested
    @Disabled("Broken! Rewrite by use of REST Controller")
    @DisplayName("/add")
    class Add {

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
    @Disabled("Broken! Rewrite by REST Controller")
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
    @Disabled("Broken! Rewrite by REST Controller")
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
    @Disabled("Broken! Rewrite by REST Controller")
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
    class GetAllTweets {

        @Test
        void shouldReturnAllTweets() throws Exception {
            String firstTweetText = "What is happening?";
            String secondTweetText = "Spring Web Mvc is awesome!";
            List<Tweet> tweets = Lists.newArrayList(new Tweet(firstTweetText), new Tweet(secondTweetText));
            doReturn(tweets).when(tweetService).findAll();

            mockMvc.perform(get("/tweet/"))
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].text", equalTo(firstTweetText)))
                    .andExpect(jsonPath("$[1].text", equalTo(secondTweetText)));
            verify(tweetService).findAll();
        }

    }


}