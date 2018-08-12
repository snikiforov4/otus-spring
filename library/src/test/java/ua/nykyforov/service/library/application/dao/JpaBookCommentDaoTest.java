package ua.nykyforov.service.library.application.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import ua.nykyforov.service.library.core.domain.BookComment;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@SpringJUnitConfig(classes = {DataSourceConfig.class})
class JpaBookCommentDaoTest {

    private static final String TABLE_NAME = "book_comment";

    @Autowired
    private JpaBookCommentDao sut;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Sql({"/test-insert-books-1.sql"})
    void shouldInsertEntity() {
        int expectedNumberOfRows = getCountOfRowsInTable() + 1;

        sut.insert(new BookComment("Awesome book", 42));

        assertEquals(expectedNumberOfRows, getCountOfRowsInTable(),
                "wrong number of inserted rows");
    }

    @Test
    @Sql({"/test-get-all-entities-by-book-id.sql"})
    void shouldGetAllBookCommentsByBookId() {
        final int bookId = 44;

        List<BookComment> bookComments = sut.getAllByBookId(bookId);

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

    private int getCountOfRowsInTable() {
        return JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME);
    }

}