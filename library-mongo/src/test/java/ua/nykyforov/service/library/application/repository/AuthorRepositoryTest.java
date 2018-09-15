package ua.nykyforov.service.library.application.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.nykyforov.service.library.application.domain.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@SpringJUnitConfig(classes = {MongoConfig.class})
class AuthorRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AuthorRepository sut;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Author.class);
    }

    @Test
    void shouldSaveEntity() {
        String firstName = "Stephen";
        String lastName = "King";

        Author savedAuthor = sut.save(new Author(firstName, lastName));

        assertThat(savedAuthor).satisfies(a -> {
            assertThat(a).withFailMessage("saved author").isNotNull();
            assertThat(a.getId()).withFailMessage("author id").isNotNull();
        });

        Optional<Author> author = sut.findById(savedAuthor.getId());

        assertThat(author)
                .isPresent()
                .hasValueSatisfying(g -> {
                    assertThat(g.getId()).isEqualTo(savedAuthor.getId());
                    assertThat(g.getFirstName()).isEqualTo(firstName);
                    assertThat(g.getLastName()).isEqualTo(lastName);
                });
    }
}