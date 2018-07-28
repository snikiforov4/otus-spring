package ua.nykyforov.service.library.core.dao;

import ua.nykyforov.service.library.core.domain.Genre;

public interface GenreDao {

    int insert(Genre genre);

    Genre getById(int id);

}
