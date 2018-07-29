package ua.nykyforov.service.library.application.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.nykyforov.service.library.core.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@JdbcTest
@SpringJUnitConfig(classes = {DataSourceConfig.class})
class JdbcBookDaoTest {

    @Autowired
    private JdbcBookDao sut;

    @Test
    void shouldInsertEntity() {
        int updated = sut.insert(new Book("Java Puzzlers"));

        assertEquals(1, updated, "wrong number of updated rows");
    }

    @Test
    @Sql({"/test-add-books.sql"})
    void shouldGetEntityById() {
        final int expectedBookId = 42;
        String expectedBookTitle = "Java Puzzlers";
        Book actualBook = sut.getById(expectedBookId);
        assertThat(actualBook).isNotNull();
        assertAll(
                () -> assertThat(actualBook.getId()).isEqualTo(expectedBookId),
                () -> assertThat(actualBook.getTitle()).isEqualTo(expectedBookTitle)
        );
    }

}