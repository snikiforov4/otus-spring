package ua.nykyforov.service.library.application.shell;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.nykyforov.service.library.application.repository.AuthorRepository;
import ua.nykyforov.service.library.application.repository.BookRepository;
import ua.nykyforov.service.library.application.repository.GenreRepository;
import ua.nykyforov.service.library.core.domain.Author;
import ua.nykyforov.service.library.core.domain.Book;
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
    private BookRepository bookRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private AuthorRepository authorRepository;

    private BookCommands sut;

    @BeforeEach
    void setUp() {
        sut = new BookCommands(bookRepository, genreRepository, authorRepository);
    }

    @Nested
    @DisplayName("addBook")
    class AddBook {

        @Test
        void shouldCallRepositoryMethodWithParam() {
            String title = "It";

            sut.addBook(title, 0);

            verify(bookRepository, times(1))
                    .save(argThat(argument -> Objects.equals(argument.getTitle(), title)));
        }

        @Test
        void shouldNotCallGenreRepositoryIfGenreIdParamIsNotPositive() {
            String title = "It";
            final int genreId = 0;

            sut.addBook(title, genreId);

            verify(genreRepository, never()).findById(anyInt());
            verify(bookRepository, times(1))
                    .save(argThat(book -> !book.getGenre().isPresent()));
        }

        @Test
        void shouldGetGenreFromRepositoryIfGenreIdParamIsPositive() {
            String title = "It";
            final int genreId = 42;
            Genre genre = new Genre("Mystic");
            genre.setId(genreId);
            doReturn(Optional.of(genre)).when(genreRepository).findById(eq(genreId));

            sut.addBook(title, genreId);

            verify(genreRepository, times(1)).findById(eq(42));
            verify(bookRepository, times(1))
                    .save(argThat(book -> book.getGenre().orElseThrow(() -> new RuntimeException("Genre is not present in book")) == genre));
        }

    }

    @Nested
    @DisplayName("deleteBook")
    class DeleteBook {

        @Test
        void shouldCallRepositoryMethod() {
            final int bookId = 42;

            sut.deleteBook(bookId);

            verify(bookRepository, times(1)).deleteById(eq(bookId));
        }

    }

    @Nested
    @DisplayName("findBookByTitle")
    class FindBookByTitle {

        @Test
        void shouldCallRepositoryMethod() {
            final String title = "42";

            sut.findBookByTitle(title);

            verify(bookRepository, times(1)).findAllByTitleLike(eq(title));
        }

    }

    @Nested
    @DisplayName("addAuthorToBook")
    class AddAuthorToBook {

        @Test
        void shouldThrowExceptionIfAuthorNotFound() {
            final int authorId = 1;
            final int bookId = 2;
            doReturn(Optional.empty()).when(authorRepository).findById(eq(authorId));

            Assertions.assertThatThrownBy(() -> sut.addAuthorToBook(authorId, bookId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage(String.format("Author with id=%s not found", authorId));
        }

        @Test
        void shouldThrowExceptionIfBookNotFound() {
            final int authorId = 1;
            final int bookId = 2;
            doReturn(Optional.of(createAuthorStub(authorId))).when(authorRepository).findById(eq(authorId));
            doReturn(Optional.empty()).when(bookRepository).findById(eq(bookId));

            Assertions.assertThatThrownBy(() -> sut.addAuthorToBook(authorId, bookId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage(String.format("Book with id=%s not found", bookId));
        }

        @Test
        void shouldCallRepositoryMethod() {
            final int authorId = 1;
            final int bookId = 2;
            doReturn(Optional.of(createAuthorStub(authorId))).when(authorRepository).findById(eq(authorId));
            doReturn(Optional.of(createBookStub(bookId))).when(bookRepository).findById(eq(bookId));

            sut.addAuthorToBook(authorId, bookId);

            verify(bookRepository, times(1))
                    .save(argThat(book -> book.getAuthors().stream().map(Author::getId).count() == 1));
        }

    }

    private static Book createBookStub(int bookId) {
        Book book = new Book("Outsider");
        book.setId(bookId);
        return book;
    }

    private static Author createAuthorStub(int authorId) {
        Author author = new Author("Stephen", "King");
        author.setId(authorId);
        return author;
    }

}