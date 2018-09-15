package ua.nykyforov.service.library.application.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.nykyforov.service.library.application.domain.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@SpringJUnitConfig(classes = {MongoConfig.class})
class GenreRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GenreRepository sut;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Genre.class);
    }

    @Test
    void shouldSaveEntity() {
        final String genreName = "Horror";

        Genre savedGenre = sut.save(new Genre(genreName));

        assertThat(savedGenre).satisfies(g -> {
            assertThat(g).isNotNull();
            assertThat(g.getId()).isNotBlank();
        });

        final String id = savedGenre.getId();
        Optional<Genre> genre = sut.findById(id);

        assertThat(genre)
                .isPresent()
                .hasValueSatisfying(g -> {
                    assertThat(g.getName()).isEqualTo(genreName);
                    assertThat(g.getId()).isNotBlank();
                });
    }
}