package ua.nykyforov.twitter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.nykyforov.twitter.Main;
import ua.nykyforov.twitter.domain.Tweet;
import ua.nykyforov.twitter.dto.TweetDto;
import ua.nykyforov.twitter.service.TweetService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(tweetService);
    }

    @Nested
    @DisplayName("GET /")
    class GetAllTweets {

        @Test
        void shouldReturnAllTweets() throws Exception {
            String firstTweetText = "What is happening?";
            String secondTweetText = "Spring Web Mvc is awesome!";
            List<Tweet> tweets = Lists.newArrayList(new Tweet(firstTweetText), new Tweet(secondTweetText));
            doReturn(tweets).when(tweetService).findAll();

            mockMvc.perform(get("/tweet/"))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].text", equalTo(firstTweetText)))
                    .andExpect(jsonPath("$[1].text", equalTo(secondTweetText)));
            verify(tweetService).findAll();
        }

    }

    @Nested
    @DisplayName("POST /")
    class SaveNewEntity {

        @Test
        void shouldSaveEntity() throws Exception {
            String tweetText = "What's happening?";
            TweetDto tweetDto = new TweetDto(null, tweetText, null);
            doReturn(new Tweet(tweetText)).when(tweetService).save(any(Tweet.class));
            ObjectMapper mapper = createMapper();

            mockMvc.perform(post("/tweet/").contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(mapper.writeValueAsString(tweetDto)))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$", notNullValue()))
                    .andExpect(jsonPath("$.text", equalTo(tweetText)))
                    .andExpect(jsonPath("$.created", notNullValue()));
            verify(tweetService, times(1)).save(argThat(e ->
                    e != null && Objects.equals(tweetText, e.getText())
            ));
        }

    }

    @Nested
    @DisplayName("PUT /")
    class UpdateTweet {

        @Test
        void shouldUpdateEntity() throws Exception {
            final String tweetId = "42";
            final String tweetText = "What's happening?";
            Tweet tweet = new Tweet(tweetId, tweetText);
            doReturn(Optional.of(tweet)).when(tweetService).findById(eq(tweetId));
            ObjectMapper mapper = createMapper();

            mockMvc.perform(put("/tweet/").contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(mapper.writeValueAsString(tweet.toDto())))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$", notNullValue()))
                    .andExpect(jsonPath("$.id", equalTo(tweetId)))
                    .andExpect(jsonPath("$.text", equalTo(tweetText)))
                    .andExpect(jsonPath("$.created", notNullValue()));
            verify(tweetService, times(1)).save(argThat(e ->
                    e != null && Objects.equals(tweetId, e.getId()) && Objects.equals(tweetText, e.getText())
            ));
        }

    }

    @Nested
    @DisplayName("DELETE /{id}")
    class Delete {

        @Test
        void shouldDeleteEntity() throws Exception {
            final String tweetId = "tweetId";

            mockMvc.perform(delete("/tweet/{id}", tweetId));

            verify(tweetService, times(1)).deleteById(eq(tweetId));
        }

    }

    private ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

}