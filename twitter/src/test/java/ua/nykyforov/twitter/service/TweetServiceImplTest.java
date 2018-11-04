package ua.nykyforov.twitter.service;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.nykyforov.twitter.domain.Tweet;
import ua.nykyforov.twitter.repository.TweetRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TweetServiceImplTest {

    private static final String USER_ID_STUB = "42";

    @Mock
    private TweetRepository tweetRepository;

    private TweetService sut;

    @BeforeEach
    void setUp() {
        sut = new TweetServiceImpl(tweetRepository);
    }

    @Nested
    @DisplayName("findAll")
    class FindAll {

        @Test
        @SuppressWarnings("UnassignedFluxMonoInstance")
        void shouldCallRepository() {
            Flux<Tweet> tweetsFluxStub = Flux.fromIterable(Lists.newArrayList());
            doReturn(tweetsFluxStub).when(tweetRepository).findAll();

            Flux<Tweet> returnedTweets = sut.findAll();

            verify(tweetRepository, times(1)).findAll();
            assertThat(returnedTweets)
                    .isNotNull()
                    .isSameAs(tweetsFluxStub);
        }

    }

    @Nested
    @DisplayName("save")
    class Save {

        @Test
        @SuppressWarnings("UnassignedFluxMonoInstance")
        void shouldCallRepository() {
            Tweet argTweet = new Tweet(USER_ID_STUB, "What's happening?");
            Mono<Tweet> retTweet = Mono.just(new Tweet(USER_ID_STUB, "What's happening?"));
            doReturn(retTweet).when(tweetRepository).save(same(argTweet));

            Mono<Tweet> actualTweet = sut.save(argTweet);

            verify(tweetRepository, times(1)).save(same(argTweet));
            assertThat(actualTweet)
                    .isNotNull()
                    .isSameAs(retTweet);
        }

    }

    @Nested
    @DisplayName("findById")
    class FindById {

        @Test
        @SuppressWarnings("UnassignedFluxMonoInstance")
        void shouldCallRepository() {
            String tweetId = "42";
            Mono<Tweet> retTweet = Mono.just(new Tweet(USER_ID_STUB, "What's happening?"));
            doReturn(retTweet).when(tweetRepository).findById(eq(tweetId));

            Mono<Tweet> actualTweet = sut.findById(tweetId);

            verify(tweetRepository, times(1)).findById(eq(tweetId));
            assertThat(actualTweet)
                    .isNotNull()
                    .isSameAs(retTweet);
        }

    }

    @Nested
    @DisplayName("deleteById")
    class DeleteById {

        @Test
        @SuppressWarnings("UnassignedFluxMonoInstance")
        void shouldCallRepository() {
            Tweet tweet = new Tweet("42", "42", "Text");
            Mono<Void> expectedRes = Mono.empty();
            doReturn(expectedRes).when(tweetRepository).delete(same(tweet));

            Mono<Void> actualRes = sut.delete(tweet);

            verify(tweetRepository, times(1)).delete(same(tweet));
            assertThat(actualRes).isNotNull().isSameAs(expectedRes);
        }

    }

}