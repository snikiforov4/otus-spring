package ua.nykyforov.service.library.application;

import ua.nykyforov.service.library.domain.Genre;

public interface GenreService {

    void save(Genre genre);

    Genre getById(int id);

}
