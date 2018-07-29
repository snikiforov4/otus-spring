package ua.nykyforov.service.library.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ua.nykyforov.service.library.core.application.AuthorService;
import ua.nykyforov.service.library.core.domain.Author;

import javax.validation.constraints.NotBlank;

@ShellComponent
public class AuthorCommands {

    private final AuthorService authorService;

    @Autowired
    public AuthorCommands(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ShellMethod("Add new author.")
    public String addGenre(@NotBlank String firstName, @NotBlank String lastName) {
        authorService.save(new Author(firstName, lastName));
        return "Author was successfully saved";
    }

}
