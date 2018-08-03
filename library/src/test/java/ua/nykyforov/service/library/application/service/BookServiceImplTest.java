package ua.nykyforov.service.library.application.service;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.nykyforov.service.library.core.application.BookService;
import ua.nykyforov.service.library.core.dao.BookDao;
import ua.nykyforov.service.library.core.domain.Book;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookDao bookDao;

    private BookService sut;

    @BeforeEach
    void setUp() {
        sut = new BookServiceImpl(bookDao);
    }

    @Nested
    @DisplayName("save")
    class Save {

        @Test
        void shouldCallDao() {
            Book book = createBookStub();
            doReturn(1).when(bookDao).insert(refEq(book));

            sut.save(book);

            verify(bookDao, times(1)).insert(refEq(book));
        }

        @Test
        void shouldThrowExceptionIfDidNotReturnResultEqualsOne() {
            Book book = createBookStub();
            doReturn(0).when(bookDao).insert(refEq(book));

            assertThatThrownBy(() -> sut.save(book))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("affected number of rows: 0");
        }

    }

    @Nested
    @DisplayName("getById")
    class GetById {

        @Test
        void shouldCallDao() {
            final int id = 42;
            Book book = createBookStub();
            doReturn(book).when(bookDao).getById(eq(id));

            Book retBook = sut.getById(id);

            verify(bookDao, times(1)).getById(eq(id));
            assertThat(book).isSameAs(retBook);
        }

    }

    @Nested
    @DisplayName("deleteById")
    class DeleteById {

        @Test
        void shouldCallDao() {
            final int id = 42;
            doReturn(1).when(bookDao).deleteById(eq(id));

            sut.deleteById(id);

            verify(bookDao, times(1)).deleteById(eq(id));
        }

        @Test
        void shouldThrowExceptionIfDidNotReturnResultEqualsOne() {
            final int id = 42;
            doReturn(0).when(bookDao).deleteById(eq(id));

            assertThatThrownBy(() -> sut.deleteById(id))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("affected number of rows: 0");
        }

    }

    @Nested
    @DisplayName("findByTitle")
    class FindByTitle {

        @Test
        void shouldCallDao() {
            final String title = "42";
            List<Book> expectedBooks = Lists.newArrayList();
            doReturn(expectedBooks).when(bookDao).findByTitle(eq(title));

            Collection<Book> actualBooks = sut.findByTitle(title);

            assertThat(actualBooks).isSameAs(expectedBooks);
            verify(bookDao, times(1)).findByTitle(eq(title));
        }

    }

    private Book createBookStub() {
        return new Book("Shining");
    }

}