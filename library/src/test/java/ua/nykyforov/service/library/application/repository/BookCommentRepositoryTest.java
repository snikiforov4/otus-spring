package ua.nykyforov.service.library.application.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.nykyforov.service.library.core.domain.BookComment;

import java.time.Instant;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@SpringJUnitConfig(classes = {DataSourceConfig.class})
class BookCommentRepositoryTest {

    @Autowired
    private BookCommentRepository sut;

    @Test
    @Sql({"/test-get-all-entities-by-book-id.sql"})
    void shouldFindAllByBookId() {
        final int bookId = 44;

        Collection<BookComment> bookComments = sut.findAllByBookId(bookId);

        assertThat(bookComments)
                .isNotNull()
                .hasSize(2)
                .usingElementComparatorOnFields("id", "createDate", "text", "bookId")
                .containsExactlyInAnyOrder(
                        createBookComment(4, Instant.parse("2018-08-11T18:00:00.00Z"), "Awesome", 44),
                        createBookComment(5, Instant.parse("2018-08-11T18:00:01.00Z"), "Must have", 44)
                );
    }

    private BookComment createBookComment(int id, Instant createDate, String text, int bookId) {
        BookComment bookComment = new BookComment(text, bookId);
        bookComment.setId(id);
        bookComment.setCreateDate(createDate);
        return bookComment;
    }
}