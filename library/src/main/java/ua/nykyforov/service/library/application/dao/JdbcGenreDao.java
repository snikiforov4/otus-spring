package ua.nykyforov.service.library.application.dao;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ua.nykyforov.service.library.core.dao.GenreDao;
import ua.nykyforov.service.library.core.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcGenreDao implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public JdbcGenreDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int insert(Genre genre) {
        String sql = "INSERT INTO genre (name) VALUES(:name)";
        return jdbc.update(sql, ImmutableMap.of("name", genre.getName()));
    }

    @Override
    public Genre getById(int id) {
        String sql = "SELECT id, `name` FROM genre WHERE id = :id";
        return jdbc.queryForObject(sql, ImmutableMap.of("id", id), new GenreMapper());
    }

    public int count() {
        String sql = "SELECT count(*) FROM genre";
        return jdbc.queryForObject(sql, ImmutableMap.of(), Integer.class);
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet row, int rowNum) throws SQLException {
            Genre genre = new Genre();
            genre.setId(row.getInt("id"));
            genre.setName(row.getString("name"));
            return genre;
        }
    }
}
