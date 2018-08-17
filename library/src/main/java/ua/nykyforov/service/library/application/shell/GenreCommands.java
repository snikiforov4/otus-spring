package ua.nykyforov.service.library.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ua.nykyforov.service.library.core.application.GenreService;
import ua.nykyforov.service.library.core.domain.Genre;

import javax.validation.constraints.NotBlank;

@ShellComponent
@SuppressWarnings("UnusedReturnValue")
public class GenreCommands {

    private final GenreService genreService;

    @Autowired
    public GenreCommands(GenreService genreService) {
        this.genreService = genreService;
    }

    @ShellMethod("Add new genre.")
    Genre addGenre(@NotBlank String name) {
        Genre genre = new Genre(name);
        genreService.save(genre);
        return genre;
    }

}
