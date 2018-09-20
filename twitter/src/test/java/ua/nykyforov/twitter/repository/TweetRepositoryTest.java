package ua.nykyforov.twitter.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ua.nykyforov.twitter.domain.Tweet;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@SpringJUnitConfig(classes = {MongoConfig.class})
class TweetRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TweetRepository sut;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Tweet.class);
    }

    @Test
    void shouldSaveEntity() {
        String tweetText = "This is the text of a tweet";
        Tweet savedTweet = sut.save(new Tweet(tweetText)).block(Duration.ofSeconds(5));

        assertThat(savedTweet)
                .isNotNull()
                .satisfies(t -> {
                    assertThat(t.getId()).isNotNull();
                });

        final String id = savedTweet.getId();

        Mono<Tweet> foundTweet = sut.findById(id);

        StepVerifier.create(foundTweet)
                .assertNext(t -> {
                    assertThat(t.getId()).isEqualTo(id);
                    assertThat(t.getText()).isEqualTo(tweetText);
                    assertThat(t.getCreateDate()).isNotNull();
                })
                .expectComplete()
                .verify();
    }

}