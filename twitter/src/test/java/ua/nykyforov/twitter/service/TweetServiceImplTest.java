package ua.nykyforov.twitter.service;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.nykyforov.twitter.domain.Tweet;
import ua.nykyforov.twitter.repository.TweetRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TweetServiceImplTest {

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
        void shouldCallRepository() {
            List<Tweet> tweetsStub = Lists.newArrayList();
            doReturn(tweetsStub).when(tweetRepository).findAll();

            Collection<Tweet> returnedTweets = sut.findAll();

            verify(tweetRepository, times(1)).findAll();
            assertThat(returnedTweets)
                    .isNotNull()
                    .isSameAs(tweetsStub);
        }

    }

    @Nested
    @DisplayName("save")
    class Save {

        @Test
        void shouldCallRepository() {
            Tweet argTweet = new Tweet("What's happening?");
            Tweet retTweet = new Tweet("What's happening?");
            doReturn(retTweet).when(tweetRepository).save(same(argTweet));

            Tweet actualTweet = sut.save(argTweet);

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
        void shouldCallRepository() {
            String tweetId = "42";
            Optional<Tweet> retTweet = Optional.of(new Tweet("What's happening?"));
            doReturn(retTweet).when(tweetRepository).findById(eq(tweetId));

            Optional<Tweet> actualTweet = sut.findById(tweetId);

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
        void shouldCallRepository() {
            String tweetId = "42";
            doNothing().when(tweetRepository).deleteById(eq(tweetId));

            sut.deleteById(tweetId);

            verify(tweetRepository, times(1)).deleteById(eq(tweetId));
        }

    }

}