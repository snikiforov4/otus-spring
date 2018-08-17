package ua.nykyforov.service.library.application.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import ua.nykyforov.service.library.core.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@SpringJUnitConfig(classes = {DataSourceConfig.class})
class JpaGenreDaoTest {

    private static final String TABLE_NAME = "genre";

    @Autowired
    private JpaGenreDao sut;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void shouldInsertEntity() {
        int expectedNumberOfRows = getCountOfRowsInTable() + 1;

        sut.insert(new Genre("Adventures"));

        assertEquals(expectedNumberOfRows, getCountOfRowsInTable(),
                "wrong number of inserted rows");
    }

    @Test
    @Sql({"/test-insert-genres.sql"})
    void shouldGetEntityIfPresent() {
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
    void shouldReturnNullIfEntityIsNotPresent() {
        final int expectedGenreId = 42;

        Genre retGenre = sut.getById(expectedGenreId);

        assertThat(retGenre).isNull();
    }

    @Test
    @Sql({"/test-insert-genres.sql"})
    void shouldGetCorrectCountOfInsertedEntities() {
        sut.insert(new Genre("Computer Science"));
        int expectedNumberOfRows = getCountOfRowsInTable();

        long actualCount = sut.count();

        assertThat(actualCount).isEqualTo(expectedNumberOfRows);
    }

    private int getCountOfRowsInTable() {
        return JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME);
    }

}