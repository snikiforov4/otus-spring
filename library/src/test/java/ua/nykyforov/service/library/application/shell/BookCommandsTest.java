package ua.nykyforov.service.library.application.shell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.nykyforov.service.library.core.application.BookService;
import ua.nykyforov.service.library.core.application.GenreService;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookCommandsTest {

    @Mock
    private BookService bookService;
    @Mock
    private GenreService genreService;

    private BookCommands sut;

    @BeforeEach
    void setUp() {
        sut = new BookCommands(bookService, genreService);
    }

    @Nested
    @DisplayName("addBook")
    class AddBook {

        @Test
        void shouldWrapParameterToEntityAndPassToService() {
            String title = "It";

            sut.addBook(title, 0);

            verify(bookService, times(1))
                    .save(argThat(argument -> Objects.equals(argument.getTitle(), title)));
        }

    }

    @Nested
    @DisplayName("deleteBook")
    class DeleteBook {

        @Test
        void shouldWrapParameterToEntityAndPassToService() {
            final int bookId = 42;

            sut.deleteBook(bookId);

            verify(bookService, times(1)).deleteById(eq(bookId));
        }

    }

    @Nested
    @DisplayName("findBookByTitle")
    class FindBookByTitle {

        @Test
        void shouldWrapParameterToEntityAndPassToService() {
            final String title = "42";

            sut.findBookByTitle(title);

            verify(bookService, times(1)).findByTitleLike(eq(title));
        }

    }

}