package ua.nykyforov.twitter.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.nykyforov.twitter.domain.Tweet;

import java.util.Optional;

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
        Tweet savedTweet = sut.save(new Tweet(tweetText));
        assertThat(savedTweet).isNotNull();
        final String id = savedTweet.getId();
        assertThat(id).isNotNull();

        Optional<Tweet> author = sut.findById(id);

        assertThat(author)
                .isPresent()
                .hasValueSatisfying(g -> {
                    assertThat(g.getId()).isEqualTo(id);
                    assertThat(g.getText()).isEqualTo(tweetText);
                    assertThat(g.getCreateDate()).isNotNull();
                });
    }

}