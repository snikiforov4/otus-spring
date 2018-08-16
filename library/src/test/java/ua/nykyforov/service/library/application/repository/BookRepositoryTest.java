package ua.nykyforov.service.library.application.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.nykyforov.service.library.core.domain.Book;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@SpringJUnitConfig(classes = {DataSourceConfig.class})
class BookRepositoryTest {

    @Autowired
    private BookRepository sut;

    @Test
    @Sql({"/test-insert-books-1.sql"})
    void shouldFindAllBooksByTitleLike() {
        Collection<Book> books = sut.findAllByTitleLike("%Java%");

        assertThat(books)
                .hasSize(2)
                .usingElementComparatorOnFields("id", "title")
                .containsExactlyInAnyOrder(
                        createBook(42, "Java Puzzlers"),
                        createBook(48, "Effective Java (3rd Edition)")
                );
    }

    private Book createBook(int id, String title) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        return book;
    }
}