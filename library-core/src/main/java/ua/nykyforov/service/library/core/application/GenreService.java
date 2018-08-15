package ua.nykyforov.service.library.core.application;

import ua.nykyforov.service.library.core.domain.Genre;

public interface GenreService {

    void save(Genre genre);

    Genre getById(int id);

}
