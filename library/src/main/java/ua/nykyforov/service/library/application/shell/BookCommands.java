package ua.nykyforov.service.library.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.*;
import ua.nykyforov.service.library.core.application.BookService;
import ua.nykyforov.service.library.core.domain.Author;
import ua.nykyforov.service.library.core.domain.Book;
import ua.nykyforov.service.library.core.domain.Genre;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

@ShellComponent
public class BookCommands {

    private final BookService bookService;

    @Autowired
    public BookCommands(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod("Add new book.")
    public String addBook(@NotBlank String title) {
        bookService.save(new Book(title));
        return "Book was successfully saved";
    }

    @ShellMethod("Delete book by id.")
    public String deleteBook(@Positive int id) {
        bookService.deleteById(id);
        return "Book was successfully deleted";
    }

    @ShellMethod("Find books by title.")
    public Table findBookByTitle(@NotBlank String title) {
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
