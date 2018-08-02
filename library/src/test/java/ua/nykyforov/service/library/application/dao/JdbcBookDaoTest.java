package ua.nykyforov.service.library.application.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.nykyforov.service.library.core.domain.Book;
import ua.nykyforov.service.library.core.domain.Genre;

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
    @Sql({"/test-insert-books-1.sql"})
    void shouldGetEntityById() {
        final int expectedBookId = 42;
        String expectedBookTitle = "Java Puzzlers";
        Book actualBook = sut.getById(expectedBookId);
        assertThat(actualBook).isNotNull();
        assertAll(
                () -> assertThat(actualBook.getId()).isEqualTo(expectedBookId),
                () -> assertThat(actualBook.getTitle()).isEqualTo(expectedBookTitle),
                () -> assertThat(actualBook.getGenre()).isNotPresent()
        );
    }

    @Test
    @Sql({"/test-insert-genres.sql", "/test-insert-books-2.sql"})
    void shouldGetEntityByIdWithLinkToGenre() {
        final int expectedBookId = 42;
        String expectedBookTitle = "Java Puzzlers";
        Book actualBook = sut.getById(expectedBookId);
        assertThat(actualBook).isNotNull();
        assert actualBook.getGenre().isPresent() : "genre is empty";
        Genre genre = actualBook.getGenre().get();
        assertAll(
                () -> assertThat(actualBook.getId()).isEqualTo(expectedBookId),
                () -> assertThat(actualBook.getTitle()).isEqualTo(expectedBookTitle),
                () -> assertThat(genre.getId()).isEqualTo(44),
                () -> assertThat(genre.getName()).isEqualTo("Programming")
        );
    }

    @Test
    @Sql({"/test-insert-books-1.sql"})
    void shouldDeleteEntityById() {
        int updated = sut.deleteById(42);

        assertEquals(1, updated, "wrong number of deleted rows");
    }

}