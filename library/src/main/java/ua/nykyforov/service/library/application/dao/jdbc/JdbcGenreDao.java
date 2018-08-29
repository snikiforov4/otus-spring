package ua.nykyforov.service.library.application.dao.jdbc;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ua.nykyforov.service.library.application.annotation.Jdbc;
import ua.nykyforov.service.library.core.dao.GenreDao;
import ua.nykyforov.service.library.core.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Jdbc
public class JdbcGenreDao implements GenreDao {
    private static final Logger logger = LoggerFactory.getLogger(JdbcGenreDao.class);

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public JdbcGenreDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void insert(Genre genre) {
        String sql = "INSERT INTO usr.genre (name) VALUES(:name)";
        int updated = jdbc.update(sql, ImmutableMap.of("name", genre.getName()));
        logger.info("insert: book={} affected={}", genre, updated);
    }

    @Override
    public Genre getById(int id) {
        String sql = "SELECT id, `name` FROM usr.genre WHERE id = :id";
        return jdbc.queryForObject(sql, ImmutableMap.of("id", id), new GenreMapper());
    }

    public long count() {
        String sql = "SELECT count(0) FROM usr.genre";
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
