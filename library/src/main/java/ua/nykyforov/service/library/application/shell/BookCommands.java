package ua.nykyforov.service.library.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.*;
import ua.nykyforov.service.library.core.application.BookService;
import ua.nykyforov.service.library.core.application.GenreService;
import ua.nykyforov.service.library.core.domain.Author;
import ua.nykyforov.service.library.core.domain.Book;
import ua.nykyforov.service.library.core.domain.Genre;

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

    @Autowired
    public BookCommands(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @ShellMethod("Add new book.")
    Book addBook(@NotBlank String title,
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

    @ShellMethod("Find books by title.")
    Table findBookByTitle(@NotBlank String title) {
        Collection<Book> foundBooks = bookService.findByTitleLike(title);
        return buildBooksTable(foundBooks);
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
