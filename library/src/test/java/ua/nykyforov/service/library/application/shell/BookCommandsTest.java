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
import ua.nykyforov.service.library.core.domain.Genre;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

        @Test
        void shouldNotCallGenreServiceIfGenreIdParamIsNotPositive() {
            String title = "It";
            final int genreId = 0;

            sut.addBook(title, genreId);

            verify(genreService, never()).getById(anyInt());
            verify(bookService, times(1))
                    .save(argThat(book -> !book.getGenre().isPresent()));
        }

        @Test
        void shouldGetFromGenreServiceIfGenreIdParamIsPositive() {
            String title = "It";
            final int genreId = 42;
            Genre genre = new Genre("Mystic");
            genre.setId(genreId);
            doReturn(Optional.of(genre)).when(genreService).getById(eq(genreId));

            sut.addBook(title, genreId);

            verify(genreService, times(1)).getById(eq(42));
            verify(bookService, times(1))
                    .save(argThat(book -> book.getGenre().orElseThrow(() -> new RuntimeException("Genre is not present in book")) == genre));
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