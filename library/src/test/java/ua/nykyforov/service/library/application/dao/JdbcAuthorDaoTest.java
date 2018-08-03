package ua.nykyforov.service.library.application.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.nykyforov.service.library.core.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@JdbcTest
@SpringJUnitConfig(classes = {DataSourceConfig.class})
class JdbcAuthorDaoTest {

    @Autowired
    private JdbcAuthorDao sut;

    @Test
    void shouldInsertEntity() {
        int updated = sut.insert(new Author("Joshua", "Bloch"));

        assertEquals(1, updated, "wrong number of updated rows");
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

}