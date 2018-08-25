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

import static org.assertj.core.api.Assertions.assertThat;
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

}