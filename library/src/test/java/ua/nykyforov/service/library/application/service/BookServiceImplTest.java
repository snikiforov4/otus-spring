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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
            doNothing().when(bookDao).insert(refEq(book));

            sut.save(book);

            verify(bookDao, times(1)).insert(refEq(book));
        }

    }

    @Nested
    @DisplayName("getById")
    class GetById {


        @Test
        void shouldReturnOptionalWithPresentValueIfDaoReturnNonNullValue() {
            final int id = 42;
            Book book = createBookStub();
            doReturn(book).when(bookDao).getById(eq(id));

            Optional<Book> retBook = sut.getById(id);

            verify(bookDao, times(1)).getById(eq(id));
            assertThat(retBook)
                    .isPresent()
                    .containsSame(book);
        }

        @Test
        void shouldReturnOptionalWithNotPresentValueIfDaoReturnNullValue() {
            final int id = 42;
            doReturn(null).when(bookDao).getById(eq(id));

            Optional<Book> retBook = sut.getById(id);

            verify(bookDao, times(1)).getById(eq(id));
            assertThat(retBook).isNotPresent();
        }

    }

    @Nested
    @DisplayName("deleteById")
    class DeleteById {

        @Test
        void shouldCallDao() {
            final int id = 42;
            doNothing().when(bookDao).deleteById(eq(id));

            sut.deleteById(id);

            verify(bookDao, times(1)).deleteById(eq(id));
        }

    }

    @Nested
    @DisplayName("findByTitleLike")
    class FindByTitleLike {

        @Test
        void shouldCallDao() {
            final String title = "42";
            List<Book> expectedBooks = Lists.newArrayList();
            doReturn(expectedBooks).when(bookDao).findByTitleLike(eq(title));

            Collection<Book> actualBooks = sut.findByTitleLike(title);

            assertThat(actualBooks).isSameAs(expectedBooks);
            verify(bookDao, times(1)).findByTitleLike(eq(title));
        }

    }

    private Book createBookStub() {
        return new Book("Shining");
    }

}