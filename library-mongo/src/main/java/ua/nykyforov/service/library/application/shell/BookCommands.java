package ua.nykyforov.service.library.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.*;
import ua.nykyforov.service.library.application.domain.Author;
import ua.nykyforov.service.library.application.domain.Book;
import ua.nykyforov.service.library.application.domain.Genre;
import ua.nykyforov.service.library.application.repository.AuthorRepository;
import ua.nykyforov.service.library.application.repository.BookRepository;
import ua.nykyforov.service.library.application.repository.GenreRepository;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Objects;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@ShellComponent
@SuppressWarnings("UnusedReturnValue")
public class BookCommands {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookCommands(BookRepository bookRepository, GenreRepository genreRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    @ShellMethod("Add new book.")
    public Book addBook(@NotBlank String title,
                        @ShellOption(defaultValue = "") String genreId) {
        Book book = new Book(title);
        if (isNotBlank(genreId)) {
            genreRepository.findById(genreId).ifPresent(book::setGenre);
        }
        bookRepository.save(book);
        return book;
    }

    @ShellMethod("Delete book by id.")
    public String deleteBook(@NotBlank String id) {
        bookRepository.deleteById(id);
        return String.format("Book with id=%s was successfully deleted", id);
    }

    @ShellMethod("Find books by title.")
    public Table findBookByTitle(@NotBlank String title) {
        Collection<Book> foundBooks = bookRepository.findAllByTitleLike(title);
        return buildBooksTable(foundBooks);
    }

    @ShellMethod("Add author to book.")
    public Book addAuthorToBook(@NotBlank String authorId, @NotBlank String bookId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException(String.format("Author with id=%s not found", authorId)));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException(String.format("Book with id=%s not found", bookId)));
        book.addAuthor(author);
        return bookRepository.save(book);
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
