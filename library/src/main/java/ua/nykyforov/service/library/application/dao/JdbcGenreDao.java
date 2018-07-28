package ua.nykyforov.service.library.application.dao;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ua.nykyforov.service.library.core.dao.GenreDao;
import ua.nykyforov.service.library.core.domain.Genre;

@Repository
public class JdbcGenreDao implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public JdbcGenreDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int insert(Genre genre) {
        return jdbc.update("insert into genre (name) values(:name)",
                ImmutableMap.of("name", genre.getName()));
    }

    @Override
    public Genre getById(int id) {
        return null;
    }
}
