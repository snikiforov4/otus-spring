package ua.nykyforov.service.library.application.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.nykyforov.service.library.core.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@JdbcTest
@SpringJUnitConfig(classes = {DataSourceConfig.class})
class JdbcGenreDaoTest {

    @Autowired
    private JdbcGenreDao sut;

    @Test
    void shouldInsertEntity() {
        int updated = sut.insert(new Genre("Adventure"));

        assertEquals(1, updated, "wrong number of updated rows");
    }

    @Test
    @Sql({"/test-insert-genres.sql"})
    void shouldGetEntityById() {
        final int expectedGenreId = 42;
        String expectedGenreName = "Mystic";
        Genre retGenre = sut.getById(expectedGenreId);
        assertThat(retGenre).isNotNull();
        assertAll(
                () -> assertThat(retGenre.getName()).isEqualTo(expectedGenreName),
                () -> assertThat(retGenre.getId()).isEqualTo(expectedGenreId)
        );
    }

    @Test
    void shouldGetCorrectCountOfInsertedEntities() {
        int updated;
        updated = sut.insert(new Genre("Thriller"));
        assert updated == 1;
        updated = sut.insert(new Genre("Horror"));
        assert updated == 1;
        updated = sut.insert(new Genre("Adventures"));
        assert updated == 1;

        int actualCount = sut.count();
        assertThat(actualCount).isEqualTo(3);
    }

}