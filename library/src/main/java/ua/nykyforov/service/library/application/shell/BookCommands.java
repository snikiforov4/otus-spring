package ua.nykyforov.service.library.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.*;
import ua.nykyforov.service.library.core.application.AuthorService;
import ua.nykyforov.service.library.core.application.BookService;
import ua.nykyforov.service.library.core.application.GenreService;
import ua.nykyforov.service.library.core.domain.Author;
import ua.nykyforov.service.library.core.domain.Book;
import ua.nykyforov.service.library.core.domain.Genre;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

@ShellComponent
@SuppressWarnings("UnusedReturnValue")
public class BookCommands {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Autowired
    public BookCommands(BookService bookService, GenreService genreService, AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @Transactional
    @ShellMethod("Add new book.")
    public Book addBook(@NotBlank String title,
                        @ShellOption(defaultValue = "0") int genreId) {
        Book book = new Book(title);
        if (genreId > 0) {
            genreService.getById(genreId).ifPresent(book::setGenre);
        }
        bookService.save(book);
        return book;
    }

    @ShellMethod("Delete book by id.")
    String deleteBook(@Positive int id) {
        bookService.deleteById(id);
        return String.format("Book with id=%s was successfully deleted", id);
    }

    @Transactional
    @ShellMethod("Find books by title.")
    public Table findBookByTitle(@NotBlank String title) {
        Collection<Book> foundBooks = bookService.findByTitleLike(title);
        return buildBooksTable(foundBooks);
    }

    @Transactional
    @ShellMethod("Add author to book.")
    public Book addAuthorToBook(@Positive int authorId, @Positive int bookId) {
        Author author = authorService.getById(authorId)
                .orElseThrow(() -> new RuntimeException(String.format("Author with id=%s not found", authorId)));
        Book book = bookService.getById(bookId)
                .orElseThrow(() -> new RuntimeException(String.format("Book with id=%s not found", bookId)));
        book.addAuthor(author);
        bookService.save(book);
        return book;
    }

    private Table buildBooksTable(Collection<Book> books) {
        Object[][] data = new Object[books.size() + 1][];
        int idx = 0;
        data[idx++] = new String[]{"ID", "Title", "Genre", "Authors"};
        for (Book book : books) {
            String authorsList = book.getAuthors().stream().filter(Objects::nonNull)
                    .map(Author::getFullName).collect(joining(", "));
            data[idx++] = new Object[] {
                    book.getId(),
                    book.getTitle(),
                    book.getGenre().map(Genre::getName).orElse(""),
                    authorsList
            };
        }
        return new TableBuilder(new ArrayTableModel(data))
                .addHeaderAndVerticalsBorders(BorderStyle.oldschool)
                .on((row, column, model) -> column == 1 || column == 3).addSizer(new AbsoluteWidthSizeConstraints(25))
                .build();
    }
}
