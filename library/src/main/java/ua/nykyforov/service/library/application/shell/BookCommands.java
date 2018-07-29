package ua.nykyforov.service.library.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ua.nykyforov.service.library.core.application.BookService;
import ua.nykyforov.service.library.core.domain.Book;

import javax.validation.constraints.NotBlank;

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

}
