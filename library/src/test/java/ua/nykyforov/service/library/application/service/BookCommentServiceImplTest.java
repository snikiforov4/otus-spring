package ua.nykyforov.service.library.application.service;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.nykyforov.service.library.core.application.BookCommentService;
import ua.nykyforov.service.library.core.dao.BookCommentDao;
import ua.nykyforov.service.library.core.domain.BookComment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookCommentServiceImplTest {

    @Mock
    private BookCommentDao bookCommentDao;

    private BookCommentService sut;

    @BeforeEach
    void setUp() {
        sut = new BookCommentServiceImpl(bookCommentDao);
    }

    @Nested
    @DisplayName("save")
    class Save {

        @Test
        void shouldCallDao() {
            BookComment bookComment = createBookCommentStub();
            doNothing().when(bookCommentDao).insert(refEq(bookComment));

            sut.save(bookComment);

            verify(bookCommentDao, times(1)).insert(refEq(bookComment));
        }

    }

    @Nested
    @DisplayName("getAllByBookId")
    class GetAllByBookId {

        @Test
        void shouldReturnListReturnedFromDao() {
            final int bookId = 42;
            List<BookComment> daoList = ImmutableList.of(createBookCommentStub());
            doReturn(daoList).when(bookCommentDao).getAllByBookId(eq(bookId));

            List<BookComment> actualList = sut.getAllByBookId(bookId);

            verify(bookCommentDao, times(1)).getAllByBookId(eq(bookId));
            assertThat(actualList).isSameAs(daoList);
        }

    }

    private BookComment createBookCommentStub() {
        return new BookComment("Simple comment for awesome book", 42);
    }

}