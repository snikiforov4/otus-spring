package ua.nykyforov.service.library.core.application;

import ua.nykyforov.service.library.core.domain.Genre;

import java.util.Optional;

public interface GenreService {

    void save(Genre genre);

    Optional<Genre> getById(int id);

}
