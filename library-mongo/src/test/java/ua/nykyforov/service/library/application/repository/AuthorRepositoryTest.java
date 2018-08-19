package ua.nykyforov.service.library.application.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.nykyforov.service.library.application.domain.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootApplication
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
        assertThat(savedAuthor).isNotNull();
        final String id = savedAuthor.getId();
        assertThat(id).isNotNull();

        Optional<Author> author = sut.findById(id);

        assertThat(author)
                .isPresent()
                .hasValueSatisfying(g -> {
                    assertThat(g.getId()).isEqualTo(id);
                    assertThat(g.getFirstName()).isEqualTo(firstName);
                    assertThat(g.getLastName()).isEqualTo(lastName);
                });
    }
}