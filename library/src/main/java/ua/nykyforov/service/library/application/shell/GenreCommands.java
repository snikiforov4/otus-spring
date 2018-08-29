package ua.nykyforov.service.library.application.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ua.nykyforov.service.library.application.repository.GenreRepository;
import ua.nykyforov.service.library.core.domain.Genre;

import javax.validation.constraints.NotBlank;

@ShellComponent
@SuppressWarnings("UnusedReturnValue")
public class GenreCommands {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreCommands(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @ShellMethod("Add new genre.")
    Genre addGenre(@NotBlank String name) {
        Genre genre = new Genre(name);
        return genreRepository.save(genre);
    }

}
