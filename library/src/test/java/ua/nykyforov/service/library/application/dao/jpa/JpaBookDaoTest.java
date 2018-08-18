package ua.nykyforov.service.library.application.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import ua.nykyforov.service.library.core.domain.Author;
import ua.nykyforov.service.library.core.domain.Book;
import ua.nykyforov.service.library.core.domain.Genre;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@SpringJUnitConfig(classes = {DataSourceConfig.class})
class JpaBookDaoTest {

    private static final String TABLE_NAME = "usr.book";

    @Autowired
    private JpaBookDao sut;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void shouldInsertEntity() {
        int expectedNumberOfRows = getCountOfRowsInTable() + 1;
        sut.insert(new Book("Java Puzzlers"));

        assertEquals(expectedNumberOfRows, getCountOfRowsInTable(),
                "wrong number of inserted rows");
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
                () -> assertThat(actualBook.getGenre()).isNotPresent(),
                () -> assertThat(actualBook.getAuthors()).isEmpty()
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
                () -> assertThat(genre.getName()).isEqualTo("Programming"),
                () -> assertThat(actualBook.getAuthors()).isEmpty()

        );
    }

    @Test
    @Sql({"/test-insert-books-and-authors.sql"})
    void shouldGetEntityByIdWithLinkToAuthors() {
        final int expectedBookId = 23;

        Book actualBook = sut.getById(expectedBookId);

        assertThat(actualBook).isNotNull();
        assertAll(
                () -> assertThat(actualBook.getId()).isEqualTo(expectedBookId),
                () -> assertThat(actualBook.getAuthors())
                        .hasSize(2)
                        .usingElementComparatorOnFields("id", "firstName", "lastName")
                        .containsExactlyInAnyOrder(
                                createAuthor(42, "Joshua", "Bloch"),
                                createAuthor(43, "Neal", "Gafter")
                        )
        );
    }

    @Test
    @Sql({"/test-insert-books-1.sql"})
    void shouldDeleteEntityById() {
        int rowsInTable = getCountOfRowsInTable();
        int expectedRowsInTable = rowsInTable - 1;

        sut.deleteById(42);

        int actualRowsInTable = getCountOfRowsInTable();
        assertEquals(expectedRowsInTable, actualRowsInTable,
                "wrong number of deleted rows");
    }

    @Test
    @Sql({"/test-insert-books-1.sql"})
    void shouldFindEntityByTitle() {
        Collection<Book> books = sut.findByTitleLike("Java");

        assertThat(books)
                .hasSize(2)
                .usingElementComparatorOnFields("id", "title")
                .containsExactlyInAnyOrder(
                        createBook(42, "Java Puzzlers"),
                        createBook(48, "Effective Java (3rd Edition)")
                );
    }

    private Author createAuthor(int id, String firstName, String lastName) {
        Author author = new Author(firstName, lastName);
        author.setId(id);
        return author;
    }

    private Book createBook(int id, String title) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        return book;
    }

    private int getCountOfRowsInTable() {
        return JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME);
    }

}