package ua.nykyforov.service.library.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ua.nykyforov.service.library.core.application.GenreService;
import ua.nykyforov.service.library.core.domain.Genre;

import javax.validation.constraints.NotBlank;

@ShellComponent
public class GenreCommands {

    private final GenreService genreService;

    @Autowired
    public GenreCommands(GenreService genreService) {
        this.genreService = genreService;
    }

    @ShellMethod("Add new genre.")
    public String addNewGenre(@NotBlank String name) {
        genreService.save(new Genre(name));
        return "Genre was successfully saved";
    }

}
