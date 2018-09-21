package ua.nykyforov.twitter.controller;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.nykyforov.twitter.Main;
import ua.nykyforov.twitter.domain.Tweet;
import ua.nykyforov.twitter.dto.TweetDto;
import ua.nykyforov.twitter.service.TweetService;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SuppressWarnings("WeakerAccess")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TweetControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    TweetService tweetService;

    private WebTestClient webClient;

    @BeforeEach
    void setup() {
        this.webClient = WebTestClient
                .bindToServer()
                .baseUrl(String.format("http://localhost:%d", port))
                .build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(tweetService);
    }

    @Nested
    @DisplayName("GET /")
    @SuppressWarnings("UnassignedFluxMonoInstance")
    class GetAllTweets {

        @Test
        void shouldReturnAllTweets() {
            String firstTweetText = "What is happening?";
            String secondTweetText = "Spring Web Mvc is awesome!";
            List<Tweet> expectedTweets = Lists.newArrayList(new Tweet(firstTweetText), new Tweet(secondTweetText));
            doReturn(Flux.fromIterable(expectedTweets)).when(tweetService).findAll();

            List<TweetDto> actualTweets = webClient.get()
                    .uri("/tweet/")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .expectBodyList(TweetDto.class)
                    .returnResult().getResponseBody();

            assertThat(actualTweets).isNotNull()
                    .usingElementComparatorOnFields("text")
                    .containsExactlyInAnyOrder(expectedTweets.stream().map(Tweet::toDto).toArray(TweetDto[]::new));
            verify(tweetService).findAll();
        }

    }

    @Nested
    @DisplayName("POST /")
    @SuppressWarnings("UnassignedFluxMonoInstance")
    class SaveNewEntity {

        @Test
        void shouldSaveEntity() {
            String tweetText = "What's happening?";
            TweetDto tweetDto = new TweetDto(null, tweetText, null);
            doReturn(Mono.just(new Tweet(tweetText))).when(tweetService).save(any(Tweet.class));

            TweetDto response = webClient.post()
                    .uri("/tweet/")
                    .body(BodyInserters.fromObject(tweetDto))
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .expectBody(TweetDto.class)
                    .returnResult().getResponseBody();

            assertThat(response).isNotNull()
                    .satisfies(t -> {
                        assertThat(t.getText()).isEqualTo(tweetText);
                        assertThat(t.getCreateDate()).isNotNull();
                    });
            verify(tweetService, times(1)).save(argThat(e ->
                    e != null && Objects.equals(tweetText, e.getText())
            ));
        }

    }

    @Nested
    @DisplayName("PUT /")
    @SuppressWarnings("UnassignedFluxMonoInstance")
    class UpdateTweet {

        @Test
        void shouldUpdateEntity() {
            final String tweetId = "42";
            final String rawTweetText = "What's happening?";
            final String editedTweetText = "What's happening? (Edited)";
            Tweet rawTweet = new Tweet(tweetId, rawTweetText);
            doReturn(Mono.just(rawTweet)).when(tweetService).findById(eq(tweetId));
            Tweet editedTweet = new Tweet(tweetId, editedTweetText);
            doReturn(Mono.just(editedTweet)).when(tweetService).save(any(Tweet.class));

            TweetDto response = webClient.put()
                    .uri("/tweet/")
                    .body(BodyInserters.fromObject(editedTweet.toDto()))
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .expectBody(TweetDto.class)
                    .returnResult().getResponseBody();

            assertThat(response).isNotNull()
                    .satisfies(t -> {
                        assertThat(t.getId()).isEqualTo(tweetId);
                        assertThat(t.getText()).isEqualTo(editedTweetText);
                        assertThat(t.getCreateDate()).isNotNull();
                    });
            verify(tweetService, times(1)).save(argThat(e ->
                    e != null && Objects.equals(tweetId, e.getId()) && Objects.equals(editedTweetText, e.getText())
            ));
        }

    }

    @Nested
    @DisplayName("DELETE /{id}")
    @SuppressWarnings("UnassignedFluxMonoInstance")
    class Delete {

        @Test
        void shouldDeleteEntity() {
            final String tweetId = "tweetId";

            webClient.delete()
                    .uri("/tweet/{id}", tweetId)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .isEmpty();
            verify(tweetService, times(1)).deleteById(eq(tweetId));
        }

    }

}