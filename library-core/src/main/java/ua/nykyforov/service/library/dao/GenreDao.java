package ua.nykyforov.service.library.dao;

import ua.nykyforov.service.library.domain.Genre;

public interface GenreDao {

    void insert(Genre genre);

    Genre getById(int id);

    int count();
}
