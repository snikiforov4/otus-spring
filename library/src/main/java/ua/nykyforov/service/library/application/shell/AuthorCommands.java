package ua.nykyforov.service.library.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ua.nykyforov.service.library.core.application.AuthorService;
import ua.nykyforov.service.library.core.domain.Author;

import javax.validation.constraints.NotBlank;

@ShellComponent
@SuppressWarnings("UnusedReturnValue")
public class AuthorCommands {

    private final AuthorService authorService;

    @Autowired
    public AuthorCommands(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ShellMethod("Add new author.")
    Author addAuthor(@NotBlank String firstName, @NotBlank String lastName) {
        Author author = new Author(firstName, lastName);
        authorService.save(author);
        return author;
    }

}
