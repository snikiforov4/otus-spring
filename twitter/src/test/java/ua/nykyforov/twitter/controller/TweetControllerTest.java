package ua.nykyforov.twitter.controller;

import com.google.common.collect.ImmutableList;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.nykyforov.twitter.Main;
import ua.nykyforov.twitter.domain.Tweet;
import ua.nykyforov.twitter.dto.TweetDto;
import ua.nykyforov.twitter.security.CustomAuthenticatedPrincipal;
import ua.nykyforov.twitter.service.TweetService;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;

@SuppressWarnings("WeakerAccess")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Main.class)
class TweetControllerTest {

    private static final String USER_ID_STUB = "42";

    @Autowired
    ApplicationContext context;

    @MockBean
    TweetService tweetService;

    private WebTestClient rest;

    @BeforeEach
    void setup() {
        CustomAuthenticatedPrincipal principal = new CustomAuthenticatedPrincipal(USER_ID_STUB, "user");
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal,
                "password", ImmutableList.of());
        this.rest = WebTestClient
                .bindToApplicationContext(this.context)
                .apply(springSecurity())
                .apply(SecurityMockServerConfigurers.mockAuthentication(authentication))
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
            List<Tweet> expectedTweets = Lists.newArrayList(
                    new Tweet(USER_ID_STUB, firstTweetText),
                    new Tweet(USER_ID_STUB, secondTweetText)
            );
            doReturn(Flux.fromIterable(expectedTweets)).when(tweetService).findAll();

            List<TweetDto> actualTweets = rest.get()
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
            doReturn(Mono.just(new Tweet(USER_ID_STUB, tweetText))).when(tweetService).save(any(Tweet.class));

            TweetDto response = rest
                    .post()
                    .uri("/tweet/")
                    .body(BodyInserters.fromObject(tweetDto))
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .expectBody(TweetDto.class)
                    .returnResult().getResponseBody();

            assertThat(response).isNotNull()
                    .satisfies(t -> {
                        assertThat(t.getText()).isEqualTo(tweetText);
                        assertThat(t.getCreateDate()).isNotNull();
                    });
            verify(tweetService, times(1)).save(argThat(e ->
                    e != null && Objects.equals(tweetText, e.getText()) && Objects.equals(e.getUserId(), "42")
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
            Tweet rawTweet = new Tweet(tweetId, USER_ID_STUB, rawTweetText);
            doReturn(Mono.just(rawTweet)).when(tweetService).findById(eq(tweetId));
            Tweet editedTweet = new Tweet(tweetId, USER_ID_STUB, editedTweetText);
            doReturn(Mono.just(editedTweet)).when(tweetService).save(any(Tweet.class));

            TweetDto response = rest.put()
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

            rest.delete()
                    .uri("/tweet/{id}", tweetId)
                    .exchange()
                    .expectStatus().isNoContent()
                    .expectBody()
                    .isEmpty();
            verify(tweetService, times(1)).deleteById(eq(tweetId));
        }

    }

}