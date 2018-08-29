package ua.nykyforov.service.library.application.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import ua.nykyforov.service.library.core.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@SpringJUnitConfig(classes = {DataSourceConfig.class})
class JpaAuthorDaoTest {

    private static final String TABLE_NAME = "usr.author";

    @Autowired
    private JpaAuthorDao sut;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void shouldInsertEntity() {
        int expectedNumberOfRows = getCountOfRowsInTable() + 1;

        sut.insert(new Author("Joshua", "Bloch"));

        assertEquals(expectedNumberOfRows, getCountOfRowsInTable(),
                "wrong number of inserted rows");
    }

    @Test
    @Sql({"/test-insert-authors.sql"})
    void shouldGetEntityById() {
        final int expectedAuthorId = 42;
        String expectedAuthorFirstName = "Joshua";
        String expectedAuthorLastName = "Bloch";
        Author actualAuthor = sut.getById(expectedAuthorId);
        assertThat(actualAuthor).isNotNull();
        assertAll(
                () -> assertThat(actualAuthor.getId()).isEqualTo(expectedAuthorId),
                () -> assertThat(actualAuthor.getFirstName()).isEqualTo(expectedAuthorFirstName),
                () -> assertThat(actualAuthor.getLastName()).isEqualTo(expectedAuthorLastName)
        );
    }

    private int getCountOfRowsInTable() {
        return JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME);
    }

}