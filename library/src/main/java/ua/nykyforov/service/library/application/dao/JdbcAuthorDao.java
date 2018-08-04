package ua.nykyforov.service.library.application.dao;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ua.nykyforov.service.library.core.dao.AuthorDao;
import ua.nykyforov.service.library.core.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class JdbcAuthorDao implements AuthorDao {
    private static final Logger logger = LoggerFactory.getLogger(JdbcAuthorDao.class);

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public JdbcAuthorDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void insert(Author author) {
        String sql = "INSERT INTO author (first_name, last_name) VALUES(:firstName, :lastName)";
        Map<String, String> params = ImmutableMap.of(
                "firstName", author.getFirstName(),
                "lastName", author.getLastName());
        int updated = jdbc.update(sql, params);
        logger.info("insert: author={} affected={}", author, updated);
    }

    @Override
    public Author getById(int id) {
        String sql = "SELECT id, first_name, last_name FROM author WHERE id = :id";
        return jdbc.queryForObject(sql, ImmutableMap.of("id", id), new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet row, int rowNum) throws SQLException {
            Author author = new Author();
            author.setId(row.getInt("id"));
            author.setFirstName(row.getString("first_name"));
            author.setLastName(row.getString("last_name"));
            return author;
        }
    }
}
