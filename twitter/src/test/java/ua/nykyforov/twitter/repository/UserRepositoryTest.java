package ua.nykyforov.twitter.repository;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ua.nykyforov.twitter.domain.User;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@SpringJUnitConfig(classes = {MongoConfig.class})
class UserRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository sut;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(User.class);
    }

    @Test
    void shouldFindUserByUsername() {
        String username = "user42";
        String password = "pass";
        List<String> roles = Lists.newArrayList("USER");
        User savedUser = sut.save(new User(username, password, roles)).block(Duration.ofSeconds(5));

        assertThat(savedUser)
                .isNotNull()
                .satisfies(t -> {
                    assertThat(t.getId()).isNotNull();
                });
        final String id = savedUser.getId();

        Mono<User> foundTweet = sut.findByUsername(username);

        StepVerifier.create(foundTweet)
                .assertNext(t -> {
                    assertThat(t.getId()).isEqualTo(id);
                    assertThat(t.getUsername()).isEqualTo(username);
                    assertThat(t.getPassword()).isEqualTo(password);
                    assertThat(t.getRoles()).containsExactlyInAnyOrder("USER");
                })
                .expectComplete()
                .verify();
    }

}